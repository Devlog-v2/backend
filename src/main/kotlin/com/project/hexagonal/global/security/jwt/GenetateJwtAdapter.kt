package com.project.hexagonal.global.security.jwt

import com.project.hexagonal.domain.account.adapter.persistence.entity.RefreshTokenEntity
import com.project.hexagonal.domain.account.adapter.persistence.repository.RefreshTokenRepository
import com.project.hexagonal.domain.account.application.port.GenetateJwtPort
import com.project.hexagonal.domain.account.adapter.web.data.response.SignInResponse
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

    companion object {
        const val ACCESS_TYPE = "access"
        const val REFRESH_TYPE = "refresh"
        const val ACCESS_EXP =  1000 * 60 * 60 * 3L  // 3 hour
        const val REFRESH_EXP = 7200 // 1 week
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun generate(email: String): SignInResponse {
        val accessToken = genrateAccessToken(email, jwtProperties.accessSecret)
        val refreshToken = genrateRefreshToken(email, jwtProperties.refreshSecret)
        val accessTokenExpiredAt = getAccessTokenExpiredAt()
        refreshTokenRepository.save(RefreshTokenEntity(refreshToken, email, REFRESH_EXP))
        return SignInResponse(accessToken, refreshToken, accessTokenExpiredAt)
    }

    private fun genrateAccessToken(sub: String, secret: Key): String =
        Jwts.builder()
            .signWith(secret, SignatureAlgorithm.HS256)
            .setSubject(sub)
            .claim("type", ACCESS_TYPE)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + ACCESS_EXP * 1000))
            .compact()

    private fun genrateRefreshToken(sub: String, secret: Key): String =
        Jwts.builder()
            .signWith(secret, SignatureAlgorithm.HS256)
            .setSubject(sub)
            .claim("type", REFRESH_TYPE)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + REFRESH_EXP * 1000))
            .compact()

    private fun getAccessTokenExpiredAt(): LocalDateTime =
        LocalDateTime.now().plusSeconds(ACCESS_EXP + 1000)

}