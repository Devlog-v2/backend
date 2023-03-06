package com.project.hexagonal.domain.account.application.usecase

import com.project.hexagonal.domain.account.adapter.presentation.data.request.SignUpRequest
import com.project.hexagonal.domain.account.adapter.presentation.data.request.toDomain
import com.project.hexagonal.domain.account.application.port.AccountPort
import com.project.hexagonal.domain.account.application.port.PasswordEncodePort
import com.project.hexagonal.domain.account.exception.DuplicateEmailException
import com.project.hexagonal.global.annotation.UseCase

@UseCase
class SignUpUseCase(
    private val accountPort: AccountPort,
    private val passwordEncodePort: PasswordEncodePort
) {

    fun execute(request: SignUpRequest): Long {
        if (accountPort.existsAccountByEmail(request.email)) {
            throw DuplicateEmailException()
        }
        return accountPort.saveAccount(request.toDomain(), passwordEncodePort.encode(request.password)).idx
    }

}