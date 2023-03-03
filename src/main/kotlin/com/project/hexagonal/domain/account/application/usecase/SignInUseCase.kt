package com.project.hexagonal.domain.account.application.usecase

import com.project.hexagonal.domain.account.adapter.web.data.request.SignInRequest
import com.project.hexagonal.domain.account.adapter.web.data.response.SignInResponse
import com.project.hexagonal.domain.account.application.port.GenetateJwtPort
import com.project.hexagonal.domain.account.application.port.PasswordEncodePort
import com.project.hexagonal.domain.account.application.port.QueryAccountPort
import com.project.hexagonal.domain.account.exception.PasswordNotCorrectException
import com.project.hexagonal.global.annotation.UseCase

@UseCase
class SignInUseCase(
    private val accountPort: QueryAccountPort,
    private val genetateJwtPort: GenetateJwtPort,
    private val passwordEncodePort: PasswordEncodePort
) {

    fun execute(request: SignInRequest): SignInResponse {
        val account = accountPort.findAccountByEmail(request.email)
        if (!passwordEncodePort.match(account.encodedPassword, request.password)) {
            throw PasswordNotCorrectException()
        }
        return genetateJwtPort.generate(account.email)
    }

}