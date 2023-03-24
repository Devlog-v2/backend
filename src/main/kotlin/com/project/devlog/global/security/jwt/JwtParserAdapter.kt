package com.project.devlog.global.security.jwt

import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import com.project.devlog.domain.account.application.port.JwtParserPort
import com.project.devlog.global.security.jwt.exception.InvalidTokenException
import com.project.devlog.global.security.jwt.property.JwtProperties
import com.project.devlog.global.security.principal.AccountDetailsService
import com.project.devlog.global.security.principal.AdminDetailsService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import javax.servlet.http.HttpServletRequest

@Component
class JwtParserAdapter(
    private val jwtProperties: JwtProperties,
    private val accountDetailsService: AccountDetailsService,
    private val adminDetailsService: AdminDetailsService
): JwtParserPort {

    override fun parseAccessToken(request: HttpServletRequest): String? =
        request.getHeader(JwtProperties.tokenHeader)
            .let { it ?: return null }
            .let { if (it.startsWith(JwtProperties.tokenPrefix)) it.replace(JwtProperties.tokenPrefix, "") else null }

    override fun parseRefreshToken(refreshToken: String): String? =
            if (refreshToken.startsWith(JwtProperties.tokenPrefix)) refreshToken.replace(JwtProperties.tokenPrefix, "") else null

    override fun authentication(accessToken: String): Authentication =
        getDetails(getTokenBody(accessToken, jwtProperties.accessSecret))
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

    private fun getDetails(body: Claims): UserDetails =
        when (body.get(JwtProperties.authority, String::class.java)) {
            Authority.ROLE_ACCOUNT.name -> accountDetailsService.loadUserByUsername(body.subject)
            Authority.ROLE_ADMIN.name -> adminDetailsService.loadUserByUsername(body.subject)
            else -> throw InvalidTokenException()
        }

}