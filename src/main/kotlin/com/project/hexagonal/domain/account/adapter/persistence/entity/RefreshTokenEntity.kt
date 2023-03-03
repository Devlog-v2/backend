package com.project.hexagonal.domain.account.adapter.persistence.entity

import com.project.hexagonal.domain.account.RefreshToken
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash("refresh_token")
class RefreshTokenEntity(
    @Id
    val refreshToken: String,
    val accountEmail: String,
    @TimeToLive
    val expiredAt: Int
)

fun RefreshTokenEntity.toDomain() = RefreshToken(refreshToken = refreshToken, accountEmail = accountEmail, expiredAt = expiredAt)
