package com.project.devlog.global.security.principal

import com.project.devlog.domain.account.adapter.persistence.repository.AccountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException
import java.util.UUID

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class AccountDetailsService(
    private val accountRepository: AccountRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        AccountDetails(accountRepository.findByIdOrNull(UUID.fromString(username)) ?: throw RuntimeException() )

}