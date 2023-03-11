package com.project.devlog.domain.account.application.port

import org.springframework.security.core.Authentication
import javax.servlet.http.HttpServletRequest

interface JwtParserPort {

    fun parseAccessToken(request: HttpServletRequest): String?
    fun parseRefershToken(refreshToken: String): String?
    fun authentication(accessToken: String): Authentication
    fun isRefreshTokenExpired(refreshToken: String): Boolean

}