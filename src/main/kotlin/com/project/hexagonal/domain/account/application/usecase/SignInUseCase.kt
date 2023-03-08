package com.project.hexagonal.domain.account.application.usecase

import com.project.hexagonal.domain.account.adapter.presentation.data.request.SignInRequest
import com.project.hexagonal.domain.account.adapter.presentation.data.response.SignInResponse
import com.project.hexagonal.domain.account.application.port.GenetateJwtPort
import com.project.hexagonal.domain.account.application.port.PasswordEncodePort
import com.project.hexagonal.domain.account.application.port.QueryAccountPort
import com.project.hexagonal.domain.account.exception.AccountNotFoundException
import com.project.hexagonal.domain.account.exception.PasswordNotCorrectException
import com.project.hexagonal.global.annotation.ReadOnlyUseCase

@ReadOnlyUseCase
class SignInUseCase(
    private val queryAccountPort: QueryAccountPort,
    private val genetateJwtPort: GenetateJwtPort,
    private val passwordEncodePort: PasswordEncodePort
) {

    fun execute(request: SignInRequest): SignInResponse {
        val account = queryAccountPort.queryAccountByEmail(request.email) ?: throw AccountNotFoundException()
        if (!passwordEncodePort.isPasswordMatch(request.password, account.encodedPassword)) {
            throw PasswordNotCorrectException()
        }
        return genetateJwtPort.generate(account.idx)
    }

}