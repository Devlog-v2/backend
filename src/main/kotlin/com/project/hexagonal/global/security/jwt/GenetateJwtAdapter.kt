package com.project.hexagonal.global.security.jwt

import com.project.hexagonal.domain.account.adapter.persistence.entity.RefreshTokenEntity
import com.project.hexagonal.domain.account.adapter.persistence.repository.RefreshTokenRepository
import com.project.hexagonal.domain.account.application.port.GenetateJwtPort
import com.project.hexagonal.domain.account.adapter.presentation.data.response.SignInResponse
import com.project.hexagonal.global.security.jwt.property.JwtProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.security.Key
import java.time.LocalDateTime
import java.util.Date

@Component
class GenetateJwtAdapter(
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository
): GenetateJwtPort {

    @Transactional(rollbackFor = [Exception::class])
    override fun generate(email: String): SignInResponse {
        val accessToken = genrateAccessToken(email, jwtProperties.accessSecret)
        val refreshToken = genrateRefreshToken(email, jwtProperties.refreshSecret)
        val accessTokenExpiredAt = getAccessTokenExpiredAt()
        refreshTokenRepository.save(RefreshTokenEntity(refreshToken, email, jwtProperties.refreshExp))
        return SignInResponse(accessToken, refreshToken, accessTokenExpiredAt)
    }

    private fun genrateAccessToken(sub: String, secret: Key): String =
        Jwts.builder()
            .signWith(secret, SignatureAlgorithm.HS256)
            .setSubject(sub)
            .claim("type", JwtProperties.accessType)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.accessExp * 1000))
            .compact()

    private fun genrateRefreshToken(sub: String, secret: Key): String =
        Jwts.builder()
            .signWith(secret, SignatureAlgorithm.HS256)
            .setSubject(sub)
            .claim("type", JwtProperties.refreshType)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.refreshExp * 1000))
            .compact()

    private fun getAccessTokenExpiredAt(): LocalDateTime =
        LocalDateTime.now().plusSeconds(jwtProperties.accessExp.toLong())

}