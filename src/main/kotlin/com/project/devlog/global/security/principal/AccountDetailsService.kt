package com.project.devlog.global.security.principal

import com.project.devlog.domain.account.adapter.persistence.repository.AccountRepository
import com.project.devlog.domain.account.exception.AccountNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class AccountDetailsService(
    private val accountRepository: AccountRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        println(username)
        println(UUID.fromString(username))
        println(UUID.fromString(username) == UUID.fromString("3d4782cb-531c-482e-a69f-c6032367ea71"))

        val account = accountRepository.findByIdOrNull(UUID.fromString(username)) ?: throw AccountNotFoundException()

        println(account.idx)

        return AccountDetails(
            account.idx
        )
    }

}