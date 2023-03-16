package com.project.devlog.domain.post.usecase

import com.project.devlog.domain.account.adapter.persistence.entity.AccountEntity
import com.project.devlog.domain.account.adapter.persistence.repository.AccountRepository
import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import com.project.devlog.domain.account.adapter.presentation.data.response.SignInResponse
import com.project.devlog.domain.account.application.port.AccountSecurityPort
import com.project.devlog.domain.account.application.port.GenerateJwtPort
import com.project.devlog.domain.account.application.port.JwtParserPort
import com.project.devlog.domain.post.Post
import com.project.devlog.domain.post.adapter.presentation.data.request.WritePostRequest
import com.project.devlog.domain.post.application.port.CommandPostPort
import com.project.devlog.domain.post.application.usecase.SavePostUseCase
import com.project.devlog.global.security.principal.AccountDetails
import com.project.devlog.global.security.principal.AccountDetailsService
import com.project.devlog.infrastructure.s3.application.port.S3UploadPort
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.io.File
import java.io.FileInputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class SavePostUseCaseTest: BehaviorSpec({
    val commandPostPort = mockk<CommandPostPort>()
    val accountSecurityPort = mockk<AccountSecurityPort>()
    val s3UploadPort = mockk<S3UploadPort>()
    val savePostUseCase = SavePostUseCase(commandPostPort, accountSecurityPort, s3UploadPort)
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
    val postIdx = UUID.randomUUID()
    val title = "test title"
    val content = "test content"
    val tag = mutableListOf("test tag1", "test tag2")
    val images = mutableListOf("test image1", "test image2")
    val createdAt = LocalDate.now()

    // post_image
    val fileName = "test_image"
    val contentType = "image/jpg"
    val filePath = "src/test/resources/image/test_image.jpg"
    val file = MockMultipartFile(
        fileName, "image/test_image.jpg", contentType, FileInputStream(File(filePath))
    )

    Given("account, writePostRequest, file이 주어졌을때") {
        val accountEntity = AccountEntity(accountIdx, email, password, name, Authority.ROLE_ACCOUNT)
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

        val postRequest = WritePostRequest(title, content, tag)
        val post = Post(postIdx, title, content, accountIdx, tag, images, createdAt)

        every { accountSecurityPort.getCurrentAccountIdx() } returns accountIdx
        every { s3UploadPort.uploadFile(mutableListOf(file), "post/") } returns mutableListOf(String())
        every { commandPostPort.savePost(any()) } returns post

        When("게시글 생성을 요청하면") {
            val result = savePostUseCase.execute(mutableListOf(file), postRequest)

            Then("s3에 이미지 저장 로직이 실행이 되야한다.") {
                verify(exactly = 1) { s3UploadPort.uploadFile(mutableListOf(file), "post/") }
            }

            Then("게시글이 저장 되어야 한다.") {
                verify(exactly = 1) { commandPostPort.savePost(any()) }
            }

            Then("게시글 idx는 result와 같아야 한다.") {
                postIdx shouldBe result
            }
        }
    }
})
