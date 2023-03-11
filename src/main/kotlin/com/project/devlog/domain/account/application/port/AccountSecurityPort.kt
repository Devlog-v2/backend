package com.project.devlog.domain.account.application.port

import java.util.UUID

interface AccountSecurityPort {

    fun getCurrentAccountIdx(): UUID

}