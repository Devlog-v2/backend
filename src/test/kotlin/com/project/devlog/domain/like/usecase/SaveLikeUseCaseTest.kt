package com.project.devlog.domain.like.usecase

import com.project.devlog.domain.account.adapter.persistence.entity.AccountEntity
import com.project.devlog.domain.account.adapter.persistence.repository.AccountRepository
import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import com.project.devlog.domain.account.adapter.presentation.data.response.SignInResponse
import com.project.devlog.domain.account.application.port.AccountSecurityPort
import com.project.devlog.domain.account.application.port.GenerateJwtPort
import com.project.devlog.domain.account.application.port.JwtParserPort
import com.project.devlog.domain.like.Like
import com.project.devlog.domain.like.application.port.CommandLikePort
import com.project.devlog.domain.like.application.port.QueryLikePort
import com.project.devlog.domain.like.application.usecase.SaveLikeUseCase
import com.project.devlog.domain.like.exception.DuplicateLikeException
import com.project.devlog.global.security.principal.AccountDetails
import com.project.devlog.global.security.principal.AccountDetailsService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.time.LocalDateTime
import java.util.*

class SaveLikeUseCaseTest: BehaviorSpec({
    val commandLikePort = mockk<CommandLikePort>()
    val queryLikePort = mockk<QueryLikePort>()
    val accountSecurityPort = mockk<AccountSecurityPort>()
    val saveLikeUseCaseTest = SaveLikeUseCase(commandLikePort, queryLikePort, accountSecurityPort)
    val accountRepository = mockk<AccountRepository>(relaxed = true)
    val generateJwtPort = mockk<GenerateJwtPort>()
    val jwtParserPort = mockk<JwtParserPort>()
    val accountDetailsService = mockk<AccountDetailsService>()

    // account
    val accountIdx = UUID.randomUUID()
    val email = "test@test.com"
    val password = "test password"
    val name = "test name"

    // like
    val likeIdx = 1L
    val isLiked = false

    Given("account와 postIdx가 주어졌을때") {
        val postIdx = UUID.randomUUID()

        val accountEntity = AccountEntity(accountIdx, email, password, name, null, null,  null, null, null, Authority.ROLE_ACCOUNT)
        every { accountRepository.save(accountEntity) } returns accountEntity
        accountRepository.save(accountEntity)

        val signInResponse = SignInResponse(
            accessToken = "sdfsfs",
            refreshToken = "safsdf",
            accessTokenExpiredAt = LocalDateTime.now()
        )

        every { generateJwtPort.generate(accountIdx, Authority.ROLE_ACCOUNT) } returns signInResponse
        val token = generateJwtPort.generate(accountEntity.idx, Authority.ROLE_ACCOUNT)

        val accountDetails = AccountDetails(accountIdx)
        every { accountDetailsService.loadUserByUsername(accountIdx.toString()) } returns accountDetails
        val userDetails = accountDetailsService.loadUserByUsername(accountIdx.toString())
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)

        every { jwtParserPort.authentication(token.accessToken) } returns usernamePasswordAuthenticationToken
        val authentication = jwtParserPort.authentication(token.accessToken)
        SecurityContextHolder.getContext().authentication = authentication

        val likeDomain = Like(likeIdx, isLiked, accountIdx, postIdx)

        every { accountSecurityPort.getCurrentAccountIdx() } returns accountIdx
        every { queryLikePort.queryExistsByAccountIdxAndPostIdx(accountIdx, postIdx) } returns false
        every { commandLikePort.saveLike(any()) } returns likeDomain

        When("게시글 좋아요 요청을 하면") {
            saveLikeUseCaseTest.execute(postIdx)

            Then("좋아요 수가 늘어나야한다.") {
                verify(exactly = 1) { commandLikePort.saveLike(any()) }
            }
        }

        When("이미 좋아요를 누른 계정이라면") {
            every { queryLikePort.queryExistsByAccountIdxAndPostIdx(accountIdx, postIdx) } returns true

            Then("DuplicateLikeException이 터져야 한다.") {
                shouldThrow<DuplicateLikeException> {
                    saveLikeUseCaseTest.execute(postIdx)
                }
            }
        }
    }
})