package com.project.hexagonal.domain.account.application.port

import java.util.UUID

interface AccountSecurityPort {

    fun getCurrentAccountIdx(): UUID

}