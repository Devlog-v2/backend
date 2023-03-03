package com.project.hexagonal.domain.account.application.usecase

import com.project.hexagonal.domain.account.adapter.web.data.request.SignUpRequest
import com.project.hexagonal.domain.account.adapter.web.data.request.toDomain
import com.project.hexagonal.domain.account.application.port.AccountPort
import com.project.hexagonal.domain.account.application.port.PasswordEncodePort
import com.project.hexagonal.global.annotation.UseCase

@UseCase
class SignUpUseCase(
    private val accountPort: AccountPort,
    private val passwordEncodePort: PasswordEncodePort
) {

    fun execute(request: SignUpRequest): Long =
        accountPort.existsAccountByEmail(request.email)
            .let { accountPort.saveAccount(request.toDomain(), passwordEncodePort.encode(request.password)).idx }

}