package com.project.devlog.domain.account.usecase

import com.project.devlog.domain.account.RefreshToken
import com.project.devlog.domain.account.adapter.presentation.data.response.SignInResponse
import com.project.devlog.domain.account.application.port.GenerateJwtPort
import com.project.devlog.domain.account.application.port.JwtParserPort
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
    val reissueTokenUseCase = ReissueTokenUseCase(generateJwtPort, refreshTokenPort, jwtParserPort)

    Given("RefreshToken이 주어졌을때") {
        val refreshToken = "Bearer sdfsfsfsdf"
        val parsedRefreshToken = "sdfsfsfsdf"
        val accountIdx = UUID.randomUUID()
        val expiredAt = 1800
        val refreshTokenDomain = RefreshToken(parsedRefreshToken, accountIdx, expiredAt)
        val signInResponse = SignInResponse(
            accessToken = "sdfsfs",
            refreshToken = "safsdf",
            accessTokenExpiredAt = LocalDateTime.now()
        )

        every { jwtParserPort.parseRefershToken(refreshToken) } returns parsedRefreshToken
        every { refreshTokenPort.queryByRefreshToken(parsedRefreshToken) } returns refreshTokenDomain
        every { jwtParserPort.isRefreshTokenExpired(parsedRefreshToken) } returns false
        every { generateJwtPort.generate(refreshTokenDomain.accountIdx) } returns signInResponse

        When("토큰 재발급 요청을 하면") {
            val result = reissueTokenUseCase.execute(refreshToken)

            Then("토큰이 발급이 되어야 한다.") {
                verify(exactly = 1) { generateJwtPort.generate(refreshTokenDomain.accountIdx) }
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