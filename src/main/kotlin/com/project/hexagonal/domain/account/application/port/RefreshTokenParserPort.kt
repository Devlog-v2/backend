package com.project.hexagonal.domain.account.application.port

interface RefreshTokenParserPort {

    fun parseRefershToken(refreshToken: String): String
    fun isRefreshTokenExpired(refreshToken: String): Boolean

}