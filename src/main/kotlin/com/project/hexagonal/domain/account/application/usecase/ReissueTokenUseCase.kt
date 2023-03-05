package com.project.hexagonal.domain.account.application.usecase

import com.project.hexagonal.domain.account.adapter.web.data.response.SignInResponse
import com.project.hexagonal.domain.account.application.port.GenetateJwtPort
import com.project.hexagonal.domain.account.application.port.JwtParserPort
import com.project.hexagonal.domain.account.application.port.RefreshTokenPort
import com.project.hexagonal.global.annotation.UseCase
import com.project.hexagonal.global.security.jwt.exception.ExpiredRefreshTokenExcpetion

@UseCase
class ReissueTokenUseCase(
    private val genetateJwtPort: GenetateJwtPort,
    private val refreshTokenPort: RefreshTokenPort,
    private val jwtParserPort: JwtParserPort
) {

    fun execute(request: String): SignInResponse {
        val refreshToken = jwtParserPort.parseRefershToken(request)
        val refreshTokenDomain = refreshTokenPort.findByRefreshToken(refreshToken)
        if (jwtParserPort.isRefreshTokenExpired(refreshToken)) {
            throw ExpiredRefreshTokenExcpetion()
        }
        return genetateJwtPort.generate(refreshTokenDomain.accountEmail)
    }

}