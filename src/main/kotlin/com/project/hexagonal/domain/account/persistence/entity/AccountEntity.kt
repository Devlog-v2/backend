package com.project.hexagonal.domain.account.persistence.entity

import com.project.hexagonal.domain.account.presentation.data.enumType.Autority
import com.project.hexagonal.global.entity.BaseIdxEntity
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "Account")
class AccountEntity(
    val email: String,
    val encodedPassword: String,
    val name: String,
    @ElementCollection(fetch = FetchType.EAGER) // 즉시 로딩
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "Account_Authority", joinColumns = [JoinColumn(name = "idx")])
    val authority: MutableList<Autority>
) : BaseIdxEntity()
