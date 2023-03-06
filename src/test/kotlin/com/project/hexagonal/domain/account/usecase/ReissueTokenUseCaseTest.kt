package com.project.hexagonal.domain.account.usecase

import com.project.hexagonal.domain.account.RefreshToken
import com.project.hexagonal.domain.account.adapter.presentation.data.response.SignInResponse
import com.project.hexagonal.domain.account.application.port.GenetateJwtPort
import com.project.hexagonal.domain.account.application.port.JwtParserPort
import com.project.hexagonal.domain.account.application.port.RefreshTokenPort
import com.project.hexagonal.domain.account.application.usecase.ReissueTokenUseCase
import com.project.hexagonal.global.security.jwt.exception.ExpiredRefreshTokenExcpetion
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime

class ReissueTokenUseCaseTest: BehaviorSpec({
    val genetateJwtPort = mockk<GenetateJwtPort>()
    val refreshTokenPort = mockk<RefreshTokenPort>()
    val jwtParserPort = mockk<JwtParserPort>()
    val reissueTokenUseCase = ReissueTokenUseCase(genetateJwtPort, refreshTokenPort, jwtParserPort)

    Given("RefreshToken이 주어졌을때") {
        val refreshToken = "Bearer sdfsfsfsdf"
        val parsedRefreshToken = "sdfsfsfsdf"
        val email = "test@test.com"
        val expiredAt = 1800
        val refreshTokenDomain = RefreshToken(parsedRefreshToken, email, expiredAt)
        val signInResponse = SignInResponse(
            accessToken = "sdfsfs",
            refreshToken = "safsdf",
            accessTokenExpiredAt = LocalDateTime.now()
        )

        every { jwtParserPort.parseRefershToken(refreshToken) } returns parsedRefreshToken
        every { refreshTokenPort.findByRefreshToken(parsedRefreshToken) } returns refreshTokenDomain
        every { jwtParserPort.isRefreshTokenExpired(parsedRefreshToken) } returns false
        every { genetateJwtPort.generate(refreshTokenDomain.accountEmail) } returns signInResponse

        When("토큰 재발급 요청을 하면") {
            val result = reissueTokenUseCase.execute(refreshToken)

            Then("토큰이 발급이 되어야 한다.") {
                verify(exactly = 1) { genetateJwtPort.generate(refreshTokenDomain.accountEmail) }
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