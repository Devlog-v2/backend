package com.project.devlog.domain.auth.usecase

import com.project.devlog.domain.account.Account
import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import com.project.devlog.domain.account.adapter.presentation.data.request.SignUpRequest
import com.project.devlog.domain.account.application.port.CommandAccountPort
import com.project.devlog.domain.account.application.port.PasswordEncodePort
import com.project.devlog.domain.account.application.port.QueryAccountPort
import com.project.devlog.domain.account.application.usecase.SignUpUseCase
import com.project.devlog.domain.account.exception.DuplicateEmailException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class SignUpUseCaseTest: BehaviorSpec({
    val commandAccountPort = mockk<CommandAccountPort>()
    val queryAccountPort = mockk<QueryAccountPort>()
    val passwordEncodePort = mockk<PasswordEncodePort>()
    val signUpUseCase = SignUpUseCase(commandAccountPort, queryAccountPort, passwordEncodePort)

    // account
    val accountIdx = UUID.randomUUID()
    val email = "test@test.com"
    val password = "test password"
    val encodedPassword = "encoded test password"
    val name = "test name"

    Given("SignUpRequest가 주어 졌을때") {
        val request = SignUpRequest(email, password, name)
        val duplicateEmailRequest = SignUpRequest(email, password, name)
        val accountDomain = Account(accountIdx, email, password, name, null, null, null, null, null, Authority.ROLE_ACCOUNT)

        every { queryAccountPort.existsAccountByEmail(request.email) } returns false
        every { passwordEncodePort.passwordEncode(request.password) } returns encodedPassword
        every { commandAccountPort.saveAccount(any()) } returns accountDomain

        When("회원가입 요청을 하면") {

            val result = signUpUseCase.execute(request)

            Then("비밀번호가 인코딩 되어야 한다.") {
                verify(exactly = 1) { passwordEncodePort.passwordEncode(password) }
            }

            Then("계정이 생성 되어야 한다.") {
                verify(exactly = 1) { commandAccountPort.saveAccount(any()) }
            }

            Then("accountIdx이 null이 아니여야 한다.") {
                result shouldNotBe null
            }
        }

        When("중복된 이메일로 요청을 하면") {
            every { queryAccountPort.existsAccountByEmail(duplicateEmailRequest.email) } returns true

            Then("DuplicateEmailException이 터져야 한다.") {
                shouldThrow<DuplicateEmailException> {
                    signUpUseCase.execute(duplicateEmailRequest)
                }
            }
        }
    }
})