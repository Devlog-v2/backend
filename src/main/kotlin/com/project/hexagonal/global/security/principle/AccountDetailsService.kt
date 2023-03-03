package com.project.hexagonal.global.security.principle

import com.project.hexagonal.domain.account.adapter.persistence.repository.AccountRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class AccountDetailsService(
    private val accountRepository: AccountRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        AccountDetails(accountRepository.findByEmail(username) ?: throw RuntimeException() )

}