package com.project.hexagonal.domain.account.application.port

import com.project.hexagonal.domain.account.Account
import com.project.hexagonal.domain.account.adapter.persistence.entity.AccountEntity

interface CommandAccountPort {

    fun saveAccount(account: Account, encodedPassword: String): AccountEntity

}