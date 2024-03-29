package com.project.devlog.domain.account.adapter.persistence

import com.project.devlog.domain.account.Account
import com.project.devlog.domain.account.adapter.persistence.converter.AccountConverter
import com.project.devlog.domain.account.adapter.persistence.repository.AccountRepository
import com.project.devlog.domain.account.application.port.AccountPort
import com.project.devlog.global.annotation.AdapterWithTransaction
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@AdapterWithTransaction
class AccountPersistenceAdapter(
    private val accountRepository: AccountRepository,
    private val accountConverter: AccountConverter
): AccountPort {

    override fun saveAccount(account: Account): Account =
        accountConverter.toDomain(accountRepository.save(accountConverter.toEntity(account)))

    override fun existsAccountByEmail(email: String): Boolean =
        accountRepository.existsByEmail(email)

    override fun queryAccountByEmail(email: String): Account? =
        accountRepository.findByEmail(email)?.let { accountConverter.toDomain(it) }

    override fun queryAccountByIdx(idx: UUID): Account? =
        accountRepository.findByIdOrNull(idx)?.let { accountConverter.toDomain(it) }

}