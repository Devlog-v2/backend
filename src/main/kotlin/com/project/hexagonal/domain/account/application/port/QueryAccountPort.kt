package com.project.hexagonal.domain.account.application.port

import com.project.hexagonal.domain.account.Account

interface QueryAccountPort {

    fun existsAccountByEmail(email: String)
    fun findAccountByEmail(email: String): Account

}
