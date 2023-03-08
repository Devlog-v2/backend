package com.project.hexagonal.domain.account.application.port

import com.project.hexagonal.domain.account.Account
import java.util.UUID

interface QueryAccountPort {

    fun existsAccountByEmail(email: String): Boolean
    fun queryAccountByEmail(email: String): Account?
    fun queryAccountByIdx(idx: UUID): Account?

}
