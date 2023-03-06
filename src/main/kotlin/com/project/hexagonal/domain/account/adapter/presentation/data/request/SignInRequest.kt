package com.project.hexagonal.domain.account.adapter.presentation.data.request

data class SignInRequest(
    val email: String,
    val password: String
)