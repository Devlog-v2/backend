package com.project.devlog.domain.account.application.usecase

import com.project.devlog.domain.account.Account
import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import com.project.devlog.domain.account.adapter.presentation.data.request.SignUpRequest
import com.project.devlog.domain.account.application.port.CommandAccountPort
import com.project.devlog.domain.account.application.port.PasswordEncodePort
import com.project.devlog.domain.account.application.port.QueryAccountPort
import com.project.devlog.domain.account.exception.DuplicateEmailException
import com.project.devlog.global.annotation.UseCase
import java.util.*

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

        val encodedPassord = passwordEncodePort.passwordEncode(request.password)
        val account = request.let {
            Account(
                idx = UUID.randomUUID(),
                email = it.email,
                encodedPassword = encodedPassord,
                name = it.name,
                githubUrl = null,
                profileUrl = null,
                service = null,
                company = null,
                readme = null,
                authority = Authority.ROLE_ACCOUNT
            )
        }

        return commandAccountPort.saveAccount(account).idx
    }

}