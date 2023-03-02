package com.project.hexagonal.domain.account.application.port

interface QueryAccountPort {

    fun existsAccountByEmail(email: String)

}