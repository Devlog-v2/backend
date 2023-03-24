package com.project.devlog.domain.post.usecase

import com.project.devlog.domain.account.adapter.persistence.entity.AccountEntity
import com.project.devlog.domain.account.adapter.persistence.repository.AccountRepository
import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import com.project.devlog.domain.account.adapter.presentation.data.response.SignInResponse
import com.project.devlog.domain.account.application.port.GenerateJwtPort
import com.project.devlog.domain.account.application.port.JwtParserPort
import com.project.devlog.domain.post.Post
import com.project.devlog.domain.post.adapter.presentation.data.request.UpdatePostRequest
import com.project.devlog.domain.post.application.port.CommandPostPort
import com.project.devlog.domain.post.application.port.QueryPostPort
import com.project.devlog.domain.post.application.usecase.UpdatePostUseCase
import com.project.devlog.domain.post.exception.PostNotFoundException
import com.project.devlog.global.security.principal.AccountDetails
import com.project.devlog.global.security.principal.AccountDetailsService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class UpdatePostUseCaseTest: BehaviorSpec({
    val commandPostPort = mockk<CommandPostPort>()
    val queryPostPort = mockk<QueryPostPort>()
    val updatePostUseCase = UpdatePostUseCase(commandPostPort, queryPostPort)
    val accountRepository = mockk<AccountRepository>(relaxed = true)
    val generateJwtPort = mockk<GenerateJwtPort>()
    val jwtParserPort = mockk<JwtParserPort>()
    val accountDetailsService = mockk<AccountDetailsService>()

    // account
    val accountIdx = UUID.randomUUID()
    val email = "test@test.com"
    val password = "test password"
    val name = "test name"

    // post
    val title = "update test title"
    val content = "update test content"
    val tag = mutableListOf("test tag1", "test tag2")
    val createdAt = LocalDate.now()

    Given("account, postIdx, updatePostRequest, file이 주어졌을때") {
        val postIdx = UUID.randomUUID()

        val accountEntity = AccountEntity(accountIdx, email, password, name, null, null, null, null, Authority.ROLE_ACCOUNT)
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

        val updatePostRequest = UpdatePostRequest(title, content, tag)
        val postDomain = Post(postIdx, title, content, accountIdx, tag, createdAt)

        every { queryPostPort.queryPostById(postIdx) } returns postDomain
        every { commandPostPort.savePost(any()) } returns postDomain

        When("게시글 수정을 요청하면") {
            val result = updatePostUseCase.execute(postIdx, updatePostRequest )

            Then("게시글이 수정 되어야 한다.") {
                verify(exactly = 1) { commandPostPort.savePost(any()) }
            }

            Then("게시글 idx는 result와 같아야 한다.") {
                postIdx shouldBe result
            }
        }

        When("삭제된 게시글 idx를 요청하면") {
            every { queryPostPort.queryPostById(postIdx) } returns null

            Then("PostNotFoundException이 터져야 한다.") {
                shouldThrow<PostNotFoundException> {
                    updatePostUseCase.execute(postIdx, updatePostRequest )
                }
            }
        }
    }
})