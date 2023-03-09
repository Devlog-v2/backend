package com.project.hexagonal.domain.account.adapter.persistence.entity

import com.project.hexagonal.domain.account.adapter.presentation.data.enumType.Autority
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "account")
class AccountEntity(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "account_idx", columnDefinition = "BINARY(16)", nullable = false) // CHAR로 처리할 경우 32 바이트, BINARY로 처리할 경우 16 바이트
    val idx: UUID,
    val email: String,
    val encodedPassword: String,
    val name: String,
    @ElementCollection(fetch = FetchType.LAZY) // 즉시 로딩
    @Enumerated(EnumType.STRING) @CollectionTable(name = "account_authority", joinColumns = [JoinColumn(name = "account_idx")])
    val authority: MutableList<Autority>
)
