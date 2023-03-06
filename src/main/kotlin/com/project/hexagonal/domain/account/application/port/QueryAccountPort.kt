package com.project.hexagonal.domain.account.application.port

import com.project.hexagonal.domain.account.Account

interface QueryAccountPort {

    fun existsAccountByEmail(email: String): Boolean
    fun findAccountByEmail(email: String): Account?

}
