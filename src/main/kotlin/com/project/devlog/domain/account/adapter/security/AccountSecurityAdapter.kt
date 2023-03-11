package com.project.devlog.domain.account.adapter.security

import com.project.devlog.domain.account.application.port.AccountSecurityPort
import com.project.devlog.global.annotation.Adapter
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Adapter
class AccountSecurityAdapter: AccountSecurityPort {

    override fun getCurrentAccountIdx(): UUID =
        UUID.fromString(SecurityContextHolder.getContext().authentication.name)

}