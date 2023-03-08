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
import java.util.UUID

@Component
class GenetateJwtAdapter(
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository
): GenetateJwtPort {

    @Transactional(rollbackFor = [Exception::class])
    override fun generate(accountIdx: UUID): SignInResponse {
        val accessToken = genrateAccessToken(accountIdx, jwtProperties.accessSecret)
        val refreshToken = genrateRefreshToken(accountIdx, jwtProperties.refreshSecret)
        val accessTokenExpiredAt = getAccessTokenExpiredAt()
        refreshTokenRepository.save(RefreshTokenEntity(refreshToken, accountIdx, jwtProperties.refreshExp))
        return SignInResponse(accessToken, refreshToken, accessTokenExpiredAt)
    }

    private fun genrateAccessToken(accountIdx: UUID, secret: Key): String =
        Jwts.builder()
            .signWith(secret, SignatureAlgorithm.HS256)
            .setSubject(accountIdx.toString())
            .claim("type", JwtProperties.accessType)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.accessExp * 1000))
            .compact()

    private fun genrateRefreshToken(accountIdx: UUID, secret: Key): String =
        Jwts.builder()
            .signWith(secret, SignatureAlgorithm.HS256)
            .setSubject(accountIdx.toString())
            .claim("type", JwtProperties.refreshType)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.refreshExp * 1000))
            .compact()

    private fun getAccessTokenExpiredAt(): LocalDateTime =
        LocalDateTime.now().plusSeconds(jwtProperties.accessExp.toLong())

}