package com.project.hexagonal.domain.account.application.port

import com.project.hexagonal.domain.account.RefreshToken

interface RefreshTokenPort {

    fun queryByRefreshToken(refreshToken: String): RefreshToken

}