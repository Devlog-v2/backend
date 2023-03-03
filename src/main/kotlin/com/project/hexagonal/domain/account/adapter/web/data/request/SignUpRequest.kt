package com.project.hexagonal.domain.account.adapter.web.data.request

import com.project.hexagonal.domain.account.Account

data class SignUpRequest(
    val email: String,
    val password: String,
    val name: String
)

fun SignUpRequest.toDomain(): Account =
    Account(email = email, encodedPassword = password, name = name)
