package com.project.hexagonal.domain.account.adapter.persistence.converter

import com.project.hexagonal.domain.account.Account
import com.project.hexagonal.domain.account.adapter.persistence.entity.AccountEntity
import com.project.hexagonal.domain.account.adapter.presentation.data.enumType.Autority
import org.springframework.stereotype.Component
import java.util.Collections

@Component
class AccountConverter {

    fun toEntity(domain: Account, encodedPassword: String): AccountEntity =
        domain.let {
            AccountEntity(
                idx = it.idx,
                email = it.email,
                encodedPassword = encodedPassword,
                name = it.name,
                authority = Collections.singletonList(Autority.ROLE_ACCOUNT)
            )
        }

    fun toDomain(entity: AccountEntity): Account =
        entity.let {
            Account(
                idx = it.idx,
                email = it.email,
                encodedPassword = it.encodedPassword,
                name = it.name
            )
        }


}