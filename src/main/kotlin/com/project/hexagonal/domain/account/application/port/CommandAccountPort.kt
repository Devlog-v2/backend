package com.project.hexagonal.domain.account.application.port

import com.project.hexagonal.domain.account.Account

interface CommandAccountPort {

    fun saveAccount(account: Account, encodedPassword: String): Account

}