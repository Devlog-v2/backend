package com.project.devlog.domain.account.application.port

import com.project.devlog.domain.account.Account

interface CommandAccountPort {

    fun saveAccount(account: Account, encodedPassword: String): Account

}