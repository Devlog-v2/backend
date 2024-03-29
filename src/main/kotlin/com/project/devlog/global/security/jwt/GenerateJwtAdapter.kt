package com.project.devlog.global.security.jwt

import com.project.devlog.domain.account.adapter.persistence.entity.RefreshTokenEntity
import com.project.devlog.domain.account.adapter.persistence.repository.RefreshTokenRepository
import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import com.project.devlog.domain.account.application.port.GenerateJwtPort
import com.project.devlog.domain.account.adapter.presentation.data.response.SignInResponse
import com.project.devlog.global.security.jwt.property.JwtProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.security.Key
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID

@Component
class GenerateJwtAdapter(
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository
): GenerateJwtPort {

    @Transactional(rollbackFor = [Exception::class])
    override fun generate(accountIdx: UUID, authority: Authority): SignInResponse {
        val accessToken = genrateAccessToken(accountIdx, jwtProperties.accessSecret, authority)
        val refreshToken = genrateRefreshToken(accountIdx, jwtProperties.refreshSecret, authority)
        val accessTokenExpiredAt = getAccessTokenExpiredAt()
        refreshTokenRepository.save(RefreshTokenEntity(refreshToken, accountIdx, jwtProperties.refreshExp))
        return SignInResponse(accessToken, refreshToken, accessTokenExpiredAt)
    }

    private fun genrateAccessToken(accountIdx: UUID, secret: Key, authority: Authority): String =
        Jwts.builder()
            .signWith(secret, SignatureAlgorithm.HS256)
            .setSubject(accountIdx.toString())
            .claim(JwtProperties.tokenType, JwtProperties.accessType)
            .claim(JwtProperties.authority, authority.name)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.accessExp * 1000))
            .compact()

    private fun genrateRefreshToken(accountIdx: UUID, secret: Key, authority: Authority): String =
        Jwts.builder()
            .signWith(secret, SignatureAlgorithm.HS256)
            .setSubject(accountIdx.toString())
            .claim(JwtProperties.tokenType, JwtProperties.refreshType)
            .claim(JwtProperties.authority, authority.name)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtProperties.refreshExp * 1000))
            .compact()

    private fun getAccessTokenExpiredAt(): LocalDateTime =
        LocalDateTime.now().plusSeconds(jwtProperties.accessExp.toLong())

}