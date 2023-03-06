package com.project.hexagonal.domain.account

import com.project.hexagonal.domain.account.adapter.persistence.entity.AccountEntity
import com.project.hexagonal.domain.account.adapter.presentation.data.enumType.Autority
import java.util.*

data class Account(
    val email: String,
    val encodedPassword: String,
    val name: String,
)

fun Account.toEntity(encodedPassword: String): AccountEntity = AccountEntity(
    email = email,
    encodedPassword = encodedPassword,
    name = name,
    authority = Collections.singletonList(Autority.ROLE_ACCOUNT)
)