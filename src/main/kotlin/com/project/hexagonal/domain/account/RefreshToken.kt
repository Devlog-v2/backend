package com.project.hexagonal.domain.account

data class RefreshToken(
    val refreshToken: String,
    val accountEmail: String,
    val expiredAt: Int
)