package com.project.devlog.domain.account.adapter.persistence.converter

import com.project.devlog.domain.account.Account
import com.project.devlog.domain.account.adapter.persistence.entity.AccountEntity
import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import org.springframework.stereotype.Component

@Component
class AccountConverter {

    fun toEntity(domain: Account): AccountEntity =
        domain.let {
            AccountEntity(
                idx = it.idx,
                email = it.email,
                encodedPassword = it.encodedPassword,
                name = it.name,
                githubUrl = it.githubUrl,
                profileUrl = it.profileUrl,
                service = it.service,
                company = it.company,
                readme = it.readme,
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
                githubUrl = it.githubUrl,
                profileUrl = it.profileUrl,
                service = it.service,
                company = it.company,
                readme = it.readme,
                authority = entity.authority
            )
        }


}