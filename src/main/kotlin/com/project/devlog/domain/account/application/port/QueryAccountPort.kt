package com.project.devlog.domain.account.application.port

import com.project.devlog.domain.account.Account
import java.util.UUID

interface QueryAccountPort {

    fun existsAccountByEmail(email: String): Boolean
    fun queryAccountByEmail(email: String): Account?
    fun queryAccountByIdx(idx: UUID): Account?

}
