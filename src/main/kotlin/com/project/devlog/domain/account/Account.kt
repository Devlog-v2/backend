package com.project.devlog.domain.account

import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import java.util.UUID

data class Account(
    val idx: UUID,
    val email: String,
    val encodedPassword: String,
    val name: String,
    val authority: Authority
)
