package com.project.devlog.domain.auth.usecase

import com.project.devlog.domain.account.Account
import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import com.project.devlog.domain.account.adapter.presentation.data.request.SignInRequest
import com.project.devlog.domain.account.adapter.presentation.data.response.SignInResponse
import com.project.devlog.domain.account.application.port.AccountPort
import com.project.devlog.domain.account.application.port.GenerateJwtPort
import com.project.devlog.domain.account.application.port.PasswordEncodePort
import com.project.devlog.domain.account.application.usecase.SignInUseCase
import com.project.devlog.domain.account.exception.AccountNotFoundException
import com.project.devlog.domain.account.exception.PasswordNotCorrectException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime
import java.util.UUID

class SignInUseCaseTest: BehaviorSpec({
    val accountPort = mockk<AccountPort>()
    val generateJwtPort = mockk<GenerateJwtPort>()
    val passwordEncodePort = mockk<PasswordEncodePort>()
    val signInUseCase = SignInUseCase(accountPort, generateJwtPort, passwordEncodePort)

    // account
    val accountIdx = UUID.randomUUID()
    val email = "test@test.com"
    val password = "test password"
    val name = "test name"

    Given("SignInRequest가 주어졌을때") {
        val request = SignInRequest(email, password)
        val accountDomain = Account(accountIdx, email, password, name, null, null, null, null, null, Authority.ROLE_ACCOUNT)
        val signInResponse = SignInResponse(
            accessToken = "sdfsfs",
            refreshToken = "safsdf",
            accessTokenExpiredAt = LocalDateTime.now()
        )

        When("로그인 요청을 하면") {
            every { accountPort.queryAccountByEmail(request.email) } returns accountDomain
            every { passwordEncodePort.isPasswordMatch(password, accountDomain.encodedPassword) } returns true
            every { generateJwtPort.generate(accountDomain.idx, Authority.ROLE_ACCOUNT) } returns signInResponse

            val result = signInUseCase.execute(request)

            Then("토큰이 발급이 되어야 한다.") {
                verify(exactly = 1) { generateJwtPort.generate(accountDomain.idx, Authority.ROLE_ACCOUNT) }
            }

            Then("결과값이 signInResponse와 같아야 한다.") {
                result shouldBe signInResponse
            }
        }

        When("비밀번호가 틀리게 요청을 했을때") {
            every { passwordEncodePort.isPasswordMatch(password, accountDomain.encodedPassword) } returns false

            Then("PasswordNotCorrectException이 터져야 한다.") {
                shouldThrow<PasswordNotCorrectException> {
                    signInUseCase.execute(request)
                }
            }
        }

        When("이메일이 존재하지 않을때") {
            every { accountPort.queryAccountByEmail(request.email) } returns null

            Then("AccountNotFoundException이 터져야 한다.") {
                shouldThrow<AccountNotFoundException> {
                    signInUseCase.execute(request)
                }
            }
        }
    }

})