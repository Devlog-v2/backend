package com.project.hexagonal.domain.account.persistence

import com.project.hexagonal.domain.account.Account
import com.project.hexagonal.domain.account.application.port.AccountPort
import com.project.hexagonal.domain.account.persistence.entity.AccountEntity
import com.project.hexagonal.domain.account.persistence.repository.AccountRepository
import com.project.hexagonal.domain.account.toEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AccountPersistenceAdapter(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder
): AccountPort {

    override fun saveAccount(account: Account): AccountEntity =
        accountRepository.save(account.toEntity(passwordEncoder.encode(account.encodedPassword)))

    override fun existsAccountByEmail(email: String) {
        if (accountRepository.existsByEmail(email)) {
            throw RuntimeException() // TODO Basic exception 추가해야함
        }
    }

}