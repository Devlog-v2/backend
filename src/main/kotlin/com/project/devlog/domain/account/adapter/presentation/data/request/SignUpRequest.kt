package com.project.devlog.domain.account.adapter.presentation.data.request

import com.project.devlog.domain.account.Account
import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import java.util.UUID

data class SignUpRequest(
    val email: String,
    val password: String,
    val name: String
)
