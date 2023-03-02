package com.project.hexagonal.domain.account.application.usecase

import com.project.hexagonal.domain.account.Account
import com.project.hexagonal.domain.account.application.port.AccountPort
import com.project.hexagonal.global.annotation.UseCase

@UseCase
class SignUpUseCase(
    private val accountPort: AccountPort
) {

    fun execute(domain: Account): Long =
        accountPort.existsAccountByEmail(domain.email)
            .let { accountPort.saveAccount(domain).idx }

}