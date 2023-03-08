package com.project.hexagonal.domain.account.application.usecase

import com.project.hexagonal.domain.account.adapter.presentation.data.request.SignUpRequest
import com.project.hexagonal.domain.account.adapter.presentation.data.request.toDomain
import com.project.hexagonal.domain.account.application.port.CommandAccountPort
import com.project.hexagonal.domain.account.application.port.PasswordEncodePort
import com.project.hexagonal.domain.account.application.port.QueryAccountPort
import com.project.hexagonal.domain.account.exception.DuplicateEmailException
import com.project.hexagonal.global.annotation.UseCase
import java.util.UUID

@UseCase
class SignUpUseCase(
    private val commandAccountPort: CommandAccountPort,
    private val queryAccountPort: QueryAccountPort,
    private val passwordEncodePort: PasswordEncodePort
) {

    fun execute(request: SignUpRequest): UUID {
        if (queryAccountPort.existsAccountByEmail(request.email)) {
            throw DuplicateEmailException()
        }
        return commandAccountPort.saveAccount(request.toDomain(), passwordEncodePort.passwordEncode(request.password)).idx
    }

}