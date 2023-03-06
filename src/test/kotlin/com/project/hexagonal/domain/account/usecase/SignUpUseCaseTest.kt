package com.project.hexagonal.domain.account.usecase

import com.project.hexagonal.domain.account.Account
import com.project.hexagonal.domain.account.adapter.persistence.entity.AccountEntity
import com.project.hexagonal.domain.account.adapter.presentation.data.enumType.Autority
import com.project.hexagonal.domain.account.adapter.presentation.data.request.SignUpRequest
import com.project.hexagonal.domain.account.application.port.AccountPort
import com.project.hexagonal.domain.account.application.port.PasswordEncodePort
import com.project.hexagonal.domain.account.application.usecase.SignUpUseCase
import com.project.hexagonal.domain.account.exception.DuplicateEmailException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class SignUpUseCaseTest: BehaviorSpec({
    val accountPort = mockk<AccountPort>()
    val passwordEncodePort = mockk<PasswordEncodePort>()
    val signUpUseCase = SignUpUseCase(accountPort, passwordEncodePort)

    val email = "test@test.com"
    val password = "test password"
    val encodedPassword = "encoded test password"
    val name = "test name"

    Given("SignUpRequest가 주어 졌을때") {
        val request = SignUpRequest(email, password, name)
        val duplicateEmailRequest = SignUpRequest(email, password, name)
        val account = Account(email, password, name)
        val accountEntity = AccountEntity(email, password, name, Collections.singletonList(Autority.ROLE_ACCOUNT))
        val accountIdx = 0L

        every { accountPort.existsAccountByEmail(request.email) } returns false
        every { passwordEncodePort.encode(request.password) } returns encodedPassword
        every { accountPort.saveAccount(account, encodedPassword) } returns accountEntity

        When("회원가입 요청을 하면") {

            val result = signUpUseCase.execute(request)

            Then("비밀번호가 인코딩 되어야 한다.") {
                verify(exactly = 1) { passwordEncodePort.encode(password) }
            }

            Then("계정이 생성 되어야 한다.") {
                verify(exactly = 1) { accountPort.saveAccount(account, encodedPassword) }
            }

            Then("결과값이 accountIdx와 같아야 한다.") {
                result shouldBe accountIdx
            }
        }

        When("중복된 이메일로 요청을 하면") {
            every { accountPort.existsAccountByEmail(duplicateEmailRequest.email) } returns true

            Then("DuplicateEmailException이 터져야 한다.") {
                shouldThrow<DuplicateEmailException> {
                    signUpUseCase.execute(duplicateEmailRequest)
                }
            }
        }
    }
})
