package com.project.devlog.domain.account.adapter.presentation.data.request

import com.project.devlog.domain.account.Account
import java.util.UUID

data class SignUpRequest(
    val email: String,
    val password: String,
    val name: String
)

fun SignUpRequest.toDomain(): Account =
    Account(idx = UUID.randomUUID(), email = email, encodedPassword = password, name = name)
