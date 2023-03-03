package com.project.hexagonal.domain.account.adapter.web.data.request

data class SignInRequest(
    val email: String,
    val password: String
)