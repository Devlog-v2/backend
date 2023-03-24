package com.project.devlog.domain.account.adapter.persistence.entity

import com.project.devlog.domain.account.RefreshToken
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.UUID
import java.util.concurrent.TimeUnit

@RedisHash("refresh_token")
data class RefreshTokenEntity(
    @Id
    val refreshToken: String,

    val accountIdx: UUID,

    @TimeToLive(unit = TimeUnit.SECONDS)
    val expiredAt: Int
)

fun RefreshTokenEntity.toDomain() = RefreshToken(refreshToken = refreshToken, accountIdx = accountIdx, expiredAt = expiredAt)
