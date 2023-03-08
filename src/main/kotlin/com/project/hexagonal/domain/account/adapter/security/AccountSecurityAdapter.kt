package com.project.hexagonal.domain.account.adapter.security

import com.project.hexagonal.domain.account.application.port.AccountSecurityPort
import com.project.hexagonal.global.annotation.Adapter
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Adapter
class AccountSecurityAdapter: AccountSecurityPort {

    override fun getCurrentAccountIdx(): UUID =
        UUID.fromString(SecurityContextHolder.getContext().authentication.name)

}