package com.project.devlog.domain.account.application.port

import com.project.devlog.domain.account.RefreshToken

interface RefreshTokenPort {

    fun queryByRefreshToken(refreshToken: String): RefreshToken

}