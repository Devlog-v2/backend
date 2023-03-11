package com.project.devlog.domain.account

import java.util.UUID

data class RefreshToken(
    val refreshToken: String,
    val accountIdx: UUID,
    val expiredAt: Int
)