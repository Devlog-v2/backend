package com.project.devlog.domain.account.application.usecase

import com.project.devlog.domain.account.adapter.presentation.data.request.SignInRequest
import com.project.devlog.domain.account.adapter.presentation.data.response.SignInResponse
import com.project.devlog.domain.account.application.port.GenerateJwtPort
import com.project.devlog.domain.account.application.port.PasswordEncodePort
import com.project.devlog.domain.account.application.port.QueryAccountPort
import com.project.devlog.domain.account.exception.AccountNotFoundException
import com.project.devlog.domain.account.exception.PasswordNotCorrectException
import com.project.devlog.global.annotation.ReadOnlyUseCase

@ReadOnlyUseCase
class SignInUseCase(
    private val queryAccountPort: QueryAccountPort,
    private val generateJwtPort: GenerateJwtPort,
    private val passwordEncodePort: PasswordEncodePort
) {

    fun execute(request: SignInRequest): SignInResponse {
        val account = queryAccountPort.queryAccountByEmail(request.email) ?: throw AccountNotFoundException()
        if (!passwordEncodePort.isPasswordMatch(request.password, account.encodedPassword)) {
            throw PasswordNotCorrectException()
        }
        return generateJwtPort.generate(account.idx)
    }

}