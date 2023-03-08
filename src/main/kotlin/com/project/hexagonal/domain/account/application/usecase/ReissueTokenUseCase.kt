package com.project.hexagonal.domain.account.application.usecase

import com.project.hexagonal.domain.account.adapter.presentation.data.response.SignInResponse
import com.project.hexagonal.domain.account.application.port.GenetateJwtPort
import com.project.hexagonal.domain.account.application.port.JwtParserPort
import com.project.hexagonal.domain.account.application.port.RefreshTokenPort
import com.project.hexagonal.global.annotation.ReadOnlyUseCase
import com.project.hexagonal.global.security.jwt.exception.ExpiredRefreshTokenExcpetion
import com.project.hexagonal.global.security.jwt.exception.InvalidTokenTypeException

@ReadOnlyUseCase
class ReissueTokenUseCase(
    private val genetateJwtPort: GenetateJwtPort,
    private val refreshTokenPort: RefreshTokenPort,
    private val jwtParserPort: JwtParserPort
) {

    fun execute(request: String): SignInResponse {
        val refreshToken = jwtParserPort.parseRefershToken(request) ?: throw InvalidTokenTypeException()
        val refreshTokenDomain = refreshTokenPort.queryByRefreshToken(refreshToken)
        if (jwtParserPort.isRefreshTokenExpired(refreshToken)) {
            throw ExpiredRefreshTokenExcpetion()
        }
        return genetateJwtPort.generate(refreshTokenDomain.accountIdx)
    }

}