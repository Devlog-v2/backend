package com.project.hexagonal.domain.account

import java.util.UUID

data class RefreshToken(
    val refreshToken: String,
    val accountIdx: UUID,
    val expiredAt: Int
)