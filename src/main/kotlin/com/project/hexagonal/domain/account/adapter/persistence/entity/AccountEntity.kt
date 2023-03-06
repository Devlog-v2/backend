package com.project.hexagonal.domain.account.adapter.persistence.entity

import com.project.hexagonal.domain.account.Account
import com.project.hexagonal.domain.account.adapter.presentation.data.enumType.Autority
import com.project.hexagonal.global.entity.BaseIdxEntity
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "account")
class AccountEntity(
    val email: String,
    val encodedPassword: String,
    val name: String,
    @ElementCollection(fetch = FetchType.EAGER) // 즉시 로딩
    @Enumerated(EnumType.STRING) @CollectionTable(name = "account_authority", joinColumns = [JoinColumn(name = "idx")])
    val authority: MutableList<Autority>
): BaseIdxEntity()

fun AccountEntity.toDomain(): Account =
    Account(email = email, encodedPassword = encodedPassword, name = name)