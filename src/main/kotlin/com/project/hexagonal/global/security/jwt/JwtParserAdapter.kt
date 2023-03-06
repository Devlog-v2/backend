package com.project.hexagonal.global.security.jwt

import com.project.hexagonal.domain.account.application.port.JwtParserPort
import com.project.hexagonal.global.security.jwt.property.JwtProperties
import com.project.hexagonal.global.security.principle.AccountDetailsService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key
import javax.servlet.http.HttpServletRequest

@Component
class JwtParserAdapter(
    private val jwtProperties: JwtProperties,
    private val accountDetailsService: AccountDetailsService
): JwtParserPort {

    override fun parseAccessToken(request: HttpServletRequest): String? =
        request.getHeader(JwtProperties.tokenHeader)
            .let { it ?: return null }
            .let { if (it.startsWith(JwtProperties.tokenPrefix)) it.replace(JwtProperties.tokenPrefix, "") else null }

    override fun parseRefershToken(refreshToken: String): String? =
            if (refreshToken.startsWith(JwtProperties.tokenPrefix)) refreshToken.replace(JwtProperties.tokenPrefix, "") else null

    override fun authentication(accessToken: String): Authentication =
        accountDetailsService.loadUserByUsername(getTokenBody(accessToken, jwtProperties.accessSecret).subject)
            .let { UsernamePasswordAuthenticationToken(it, "", it.authorities) }

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