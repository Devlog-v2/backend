package com.project.hexagonal.global.security.jwt.property

import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.nio.charset.StandardCharsets
import java.security.Key

@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
class JwtProperties(
    accessSecret: String,
    refreshSecret: String,
    accessType: String,
    refreshType: String,
    accessExp: Long,
    refreshExp: Int,
    tokenPrefix: String
) {

    val accessSecret: Key = Keys.hmacShaKeyFor(accessSecret.toByteArray(StandardCharsets.UTF_8))
    val refreshSecret: Key = Keys.hmacShaKeyFor(refreshSecret.toByteArray(StandardCharsets.UTF_8))

    val accessType = "access"
    val refreshType = "refresh"
    val accessExp = 1000 * 60 * 60 * 3L
    val refreshExp = 7200
    val tokenPrefix = "Bearer "

}