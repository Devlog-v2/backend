package com.project.devlog.domain.account.adapter.persistence.entity

import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
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
    val authority: Authority
)
