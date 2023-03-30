package com.project.devlog.domain.comment.usecase

import com.project.devlog.domain.account.adapter.persistence.entity.AccountEntity
import com.project.devlog.domain.account.adapter.persistence.repository.AccountRepository
import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import com.project.devlog.domain.account.adapter.presentation.data.response.SignInResponse
import com.project.devlog.domain.account.application.port.AccountSecurityPort
import com.project.devlog.domain.account.application.port.GenerateJwtPort
import com.project.devlog.domain.account.application.port.JwtParserPort
import com.project.devlog.domain.comment.Comment
import com.project.devlog.domain.comment.adapter.presentation.data.request.WriteCommentRequest
import com.project.devlog.domain.comment.application.port.CommandCommentPort
import com.project.devlog.domain.comment.application.usecase.SaveCommentUseCase
import com.project.devlog.global.security.principal.AccountDetails
import com.project.devlog.global.security.principal.AccountDetailsService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.time.LocalDateTime
import java.util.*

class SaveCommentUseCaseTest: BehaviorSpec({
    val commandCommentPort = mockk<CommandCommentPort>()
    val accountSecurityPort = mockk<AccountSecurityPort>()
    val saveCommentUseCase = SaveCommentUseCase(commandCommentPort, accountSecurityPort)
    val accountRepository = mockk<AccountRepository>(relaxed = true)
    val generateJwtPort = mockk<GenerateJwtPort>()
    val jwtParserPort = mockk<JwtParserPort>()
    val accountDetailsService = mockk<AccountDetailsService>()

    // account
    val accountIdx = UUID.randomUUID()
    val email = "test@test.com"
    val password = "test password"
    val name = "test name"

    // comment
    val commentIdx = UUID.randomUUID()
    val comment = "test comment"

    Given("account, postIdx, writeCommentRequest가 주어졌을때") {
        val postIdx = UUID.randomUUID()

        val accountEntity = AccountEntity(accountIdx, email, password, name, null, null, null, null, null, Authority.ROLE_ACCOUNT)
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

        val request = WriteCommentRequest(comment)
        val commentDomain = Comment(commentIdx, request.comment, accountIdx, postIdx)

        every { accountSecurityPort.getCurrentAccountIdx() } returns accountIdx
        every { commandCommentPort.saveComment(any()) } returns commentDomain

        When("댓글 생성 요청을 하면") {
            val result = saveCommentUseCase.execute(postIdx, request)

            Then("댓글이 저장 되어야 한다.") {
                verify(exactly = 1) { commandCommentPort.saveComment(any()) }
            }

            Then("댓글 idx는 result와 같아야 한다.") {
                commentIdx shouldBe result
            }
        }
    }
})