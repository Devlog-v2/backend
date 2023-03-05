package com.project.hexagonal.domain.account.application.port

import org.springframework.security.core.Authentication
import javax.servlet.http.HttpServletRequest

interface JwtParserPort {

    fun parseAccessToken(request: HttpServletRequest): String?
    fun authentication(accessToken: String): Authentication

    fun parseRefershToken(refreshToken: String): String
    fun isRefreshTokenExpired(refreshToken: String): Boolean

}