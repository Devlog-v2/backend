package com.project.devlog.domain.account.application.usecase

import com.project.devlog.domain.account.adapter.presentation.data.response.SignInResponse
import com.project.devlog.domain.account.application.port.GenerateJwtPort
import com.project.devlog.domain.account.application.port.JwtParserPort
import com.project.devlog.domain.account.application.port.QueryAccountPort
import com.project.devlog.domain.account.application.port.RefreshTokenPort
import com.project.devlog.domain.account.exception.AccountNotFoundException
import com.project.devlog.global.annotation.ReadOnlyUseCase
import com.project.devlog.global.security.jwt.exception.ExpiredRefreshTokenExcpetion
import com.project.devlog.global.security.jwt.exception.InvalidTokenTypeException

@ReadOnlyUseCase
class ReissueTokenUseCase(
    private val generateJwtPort: GenerateJwtPort,
    private val refreshTokenPort: RefreshTokenPort,
    private val jwtParserPort: JwtParserPort,
    private val queryAccountPort: QueryAccountPort
) {

    fun execute(request: String): SignInResponse {
        val refreshToken = jwtParserPort.parseRefreshToken(request) ?: throw InvalidTokenTypeException()
        val refreshTokenDomain = refreshTokenPort.queryByRefreshToken(refreshToken)
        val account = queryAccountPort.queryAccountByIdx(refreshTokenDomain.accountIdx) ?: throw AccountNotFoundException()

        if (jwtParserPort.isRefreshTokenExpired(refreshToken)) {
            throw ExpiredRefreshTokenExcpetion()
        }
        return generateJwtPort.generate(refreshTokenDomain.accountIdx, account.authority)
    }

}