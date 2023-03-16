package com.project.devlog.domain.account.adapter.persistence.converter

import com.project.devlog.domain.account.Account
import com.project.devlog.domain.account.adapter.persistence.entity.AccountEntity
import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
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
                authority = Authority.ROLE_ACCOUNT
            )
        }

    fun toDomain(entity: AccountEntity): Account =
        entity.let {
            Account(
                idx = it.idx,
                email = it.email,
                encodedPassword = it.encodedPassword,
                name = it.name,
                authority = entity.authority
            )
        }


}