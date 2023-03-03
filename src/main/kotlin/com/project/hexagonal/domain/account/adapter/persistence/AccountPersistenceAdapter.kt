package com.project.hexagonal.domain.account.adapter.persistence

import com.project.hexagonal.domain.account.Account
import com.project.hexagonal.domain.account.application.port.AccountPort
import com.project.hexagonal.domain.account.adapter.persistence.entity.AccountEntity
import com.project.hexagonal.domain.account.adapter.persistence.entity.toDomain
import com.project.hexagonal.domain.account.adapter.persistence.repository.AccountRepository
import com.project.hexagonal.domain.account.exception.AccountNotFoundException
import com.project.hexagonal.domain.account.exception.DuplicateEmailException
import com.project.hexagonal.domain.account.toEntity
import org.springframework.stereotype.Component

@Component
class AccountPersistenceAdapter(
    private val accountRepository: AccountRepository,
): AccountPort {

    override fun saveAccount(account: Account, encodedPassword: String): AccountEntity =
        accountRepository.save(account.toEntity(encodedPassword))

    override fun existsAccountByEmail(email: String) {
        if (accountRepository.existsByEmail(email)) {
            throw DuplicateEmailException()
        }
    }

    override fun findAccountByEmail(email: String): Account =
        accountRepository.findByEmail(email)
            .let { it ?: throw AccountNotFoundException() }.toDomain()

}