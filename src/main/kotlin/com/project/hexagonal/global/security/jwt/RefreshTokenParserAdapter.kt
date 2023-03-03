package com.project.hexagonal.global.security.jwt

import com.project.hexagonal.domain.account.application.port.RefreshTokenParserPort
import com.project.hexagonal.global.security.jwt.exception.InvalidTokenTypeException
import com.project.hexagonal.global.security.jwt.property.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.security.Key

@Component
class RefreshTokenParserAdapter(
    private val jwtProperties: JwtProperties
): RefreshTokenParserPort {

    companion object {
        const val TOKEN_PREFIX = "Bearer "
    }

    override fun parseRefershToken(refreshToken: String): String =
        if (refreshToken.startsWith(TOKEN_PREFIX)) refreshToken.replace(TOKEN_PREFIX, "") else throw InvalidTokenTypeException()


    override fun isRefreshTokenExpired(refreshToken: String): Boolean {
        runCatching {
            getTokenBody(refreshToken, jwtProperties.refreshSecret).subject
        }.onFailure {
            return true
        }
        return false
    }

    private fun getTokenBody(token: String, secret: Key): Claims =
        Jwts.parserBuilder()
            .setSigningKey(secret)
            .build()
            .parseClaimsJws(token)
            .body

}