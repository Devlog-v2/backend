package com.project.devlog.domain.account.usecase

import com.project.devlog.domain.account.Account
import com.project.devlog.domain.account.adapter.persistence.entity.AccountEntity
import com.project.devlog.domain.account.adapter.persistence.repository.AccountRepository
import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import com.project.devlog.domain.account.adapter.presentation.data.request.UpdateProfileRequest
import com.project.devlog.domain.account.adapter.presentation.data.response.SignInResponse
import com.project.devlog.domain.account.application.port.*
import com.project.devlog.domain.account.application.usecase.UpdateProfileUseCase
import com.project.devlog.domain.account.exception.AccountNotFoundException
import com.project.devlog.global.security.principal.AccountDetails
import com.project.devlog.global.security.principal.AccountDetailsService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.time.LocalDateTime
import java.util.*

class UpdateProfileUseCaseTest: BehaviorSpec({
    val commandAccountPort = mockk<CommandAccountPort>()
    val queryAccountPort = mockk<QueryAccountPort>()
    val accountSecurityPort = mockk<AccountSecurityPort>()
    val updateProfileUseCase = UpdateProfileUseCase(commandAccountPort, queryAccountPort, accountSecurityPort)
    val accountRepository = mockk<AccountRepository>(relaxed = true)
    val generateJwtPort = mockk<GenerateJwtPort>()
    val jwtParserPort = mockk<JwtParserPort>()
    val accountDetailsService = mockk<AccountDetailsService>()

    // account
    val accountIdx = UUID.randomUUID()
    val email = "test@test.com"
    val password = "test password"
    val name = "test name"
    val profileUrl = "test profileUrl"
    val githubUrl = "test githubUrl"
    val service = mutableListOf("test service")
    val company = "test company"
    val readme = "test readme"

    Given("계정과 updateProfileRequest이 주어질때") {
        val accountEntity =
            AccountEntity(accountIdx, email, password, name, null, null, null, null, null, Authority.ROLE_ACCOUNT)
        every { accountRepository.save(accountEntity) } returns accountEntity
        accountRepository.save(accountEntity)

        val signInResponse = SignInResponse(
            accessToken = "sdfsfs",
            refreshToken = "safsdf",
            accessTokenExpiredAt = LocalDateTime.now()
        )

        every { generateJwtPort.generate(accountIdx, Authority.ROLE_ACCOUNT) } returns signInResponse
        val token = generateJwtPort.generate(accountEntity.idx, Authority.ROLE_ACCOUNT)

        val accountDetails = AccountDetails(accountIdx)
        every { accountDetailsService.loadUserByUsername(accountIdx.toString()) } returns accountDetails
        val userDetails = accountDetailsService.loadUserByUsername(accountIdx.toString())
        val usernamePasswordAuthenticationToken =
            UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)

        every { jwtParserPort.authentication(token.accessToken) } returns usernamePasswordAuthenticationToken
        val authentication = jwtParserPort.authentication(token.accessToken)
        SecurityContextHolder.getContext().authentication = authentication

        val accountDomain = Account(accountIdx, email, password, name, null, null, null, null, null, Authority.ROLE_ACCOUNT)
        val updateProfileReqeust = UpdateProfileRequest(name, profileUrl, githubUrl, service, company, readme)

        every { accountSecurityPort.getCurrentAccountIdx() } returns accountIdx
        every { queryAccountPort.queryAccountByIdx(accountIdx) } returns accountDomain
        every { commandAccountPort.saveAccount(any()) } returns accountDomain

        When("프로필 수정 요청을 하면") {
            val result = updateProfileUseCase.execute(updateProfileReqeust)

            Then("수정이 되어야 한다.") {
                verify(exactly = 1) { commandAccountPort.saveAccount(any()) }
            }

            Then("result와 accountIdx는 같아야 한다.") {
                result shouldBe accountIdx
            }
        }

        When("존재하지 않는 계정으로 요청을 하면") {
            every { queryAccountPort.queryAccountByIdx(accountIdx) } returns null

            Then("AccountNotFoundException이 터져야 한다.") {
                shouldThrow<AccountNotFoundException> {
                    updateProfileUseCase.execute(updateProfileReqeust)
                }
            }
        }
    }

})