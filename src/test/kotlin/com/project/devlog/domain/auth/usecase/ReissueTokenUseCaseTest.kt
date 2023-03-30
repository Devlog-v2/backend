package com.project.devlog.domain.auth.usecase

import com.project.devlog.domain.account.Account
import com.project.devlog.domain.account.RefreshToken
import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import com.project.devlog.domain.account.adapter.presentation.data.response.SignInResponse
import com.project.devlog.domain.account.application.port.GenerateJwtPort
import com.project.devlog.domain.account.application.port.JwtParserPort
import com.project.devlog.domain.account.application.port.QueryAccountPort
import com.project.devlog.domain.account.application.port.RefreshTokenPort
import com.project.devlog.domain.account.application.usecase.ReissueTokenUseCase
import com.project.devlog.global.security.jwt.exception.ExpiredRefreshTokenExcpetion
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime
import java.util.UUID

class ReissueTokenUseCaseTest: BehaviorSpec({
    val generateJwtPort = mockk<GenerateJwtPort>()
    val refreshTokenPort = mockk<RefreshTokenPort>()
    val jwtParserPort = mockk<JwtParserPort>()
    val queryAccountPort = mockk<QueryAccountPort>()
    val reissueTokenUseCase = ReissueTokenUseCase(generateJwtPort, refreshTokenPort, jwtParserPort, queryAccountPort)

    // refresh
    val refreshToken = "Bearer sdfsfsfsdf"
    val parsedRefreshToken = "sdfsfsfsdf"
    val accountIdx = UUID.randomUUID()
    val expiredAt = 1800

    // account
    val idx = UUID.randomUUID()
    val email = "test@test.com"
    val password = "test password"
    val name = "test name"

    Given("RefreshToken이 주어졌을때") {
        val refreshTokenDomain = RefreshToken(parsedRefreshToken, accountIdx, expiredAt)
        val account = Account(idx, email, password, name, null, null, null, null, null, Authority.ROLE_ACCOUNT)
        val signInResponse = SignInResponse(
            accessToken = "sdfsfs",
            refreshToken = "safsdf",
            accessTokenExpiredAt = LocalDateTime.now()
        )

        every { jwtParserPort.parseRefreshToken(refreshToken) } returns parsedRefreshToken
        every { queryAccountPort.queryAccountByIdx(refreshTokenDomain.accountIdx) } returns account
        every { refreshTokenPort.queryByRefreshToken(parsedRefreshToken) } returns refreshTokenDomain
        every { jwtParserPort.isRefreshTokenExpired(parsedRefreshToken) } returns false
        every { generateJwtPort.generate(refreshTokenDomain.accountIdx, Authority.ROLE_ACCOUNT) } returns signInResponse

        When("토큰 재발급 요청을 하면") {
            val result = reissueTokenUseCase.execute(refreshToken)

            Then("토큰이 발급이 되어야 한다.") {
                verify(exactly = 1) { generateJwtPort.generate(refreshTokenDomain.accountIdx, Authority.ROLE_ACCOUNT) }
            }

            Then("결과값이 signInResponse와 같아야 한다.") {
                result shouldBe signInResponse
            }
        }

        When("만료된 토큰으로 요청을 하면") {
            every { jwtParserPort.isRefreshTokenExpired(parsedRefreshToken) } returns true

            Then("ExpiredRefreshTokenException이 터져야한다.") {
                shouldThrow<ExpiredRefreshTokenExcpetion> {
                    reissueTokenUseCase.execute(refreshToken)
                }
            }
        }

    }

})