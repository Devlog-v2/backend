package com.project.devlog.domain.account.usecase

import com.project.devlog.domain.account.Account
import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import com.project.devlog.domain.account.adapter.presentation.data.response.ProfileDetailResponse
import com.project.devlog.domain.account.application.port.AccountSecurityPort
import com.project.devlog.domain.account.application.port.QueryAccountPort
import com.project.devlog.domain.account.application.usecase.QueryProfileDetailUseCase
import com.project.devlog.domain.account.exception.AccountNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.*

class QueryProfileDetailUseCaseTest: BehaviorSpec({
    val queryAccountPort = mockk<QueryAccountPort>()
    val accountSecurityPort = mockk<AccountSecurityPort>()
    val queryProfileDetailUseCase = QueryProfileDetailUseCase(queryAccountPort, accountSecurityPort)

    // account
    val email = "test@test.com"
    val password = "test password"
    val name = "test name"

    Given("accountIdx가 주어질때") {
        val accountIdx = UUID.randomUUID()

        val accountDomain = Account(accountIdx, email, password, name, null, null, null, null, null, Authority.ROLE_ACCOUNT)
        val profileDetailResponse = accountDomain.let {
            ProfileDetailResponse(
                accountIdx = it.idx,
                email = it.email,
                name = it.name,
                profileUrl = it.profileUrl,
                githubUrl = it.githubUrl,
                service = it.service,
                company = it.company,
                readme = it.readme,
                isMine = it.idx == accountIdx
            )
        }

        every { queryAccountPort.queryAccountByIdx(accountIdx) } returns accountDomain
        every { accountSecurityPort.getCurrentAccountIdx() } returns accountIdx

        When("프로필 디테일 요청을 하면") {
            val result = queryProfileDetailUseCase.execute(accountIdx)

            Then("result와 profileDetailResponse은 같아야 한다.") {
                result shouldBe profileDetailResponse
            }
        }

        When("존재하지 않는 계정으로 요청을 하면") {
            every { queryAccountPort.queryAccountByIdx(accountIdx) } returns null

            Then("AccountNotFoundException이 터져야 한다.") {
                shouldThrow<AccountNotFoundException> {
                    queryProfileDetailUseCase.execute(accountIdx)
                }
            }
        }
    }

})