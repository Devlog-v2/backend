package com.project.devlog.domain.account.usecase

import com.project.devlog.domain.account.Account
import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import com.project.devlog.domain.account.application.port.QueryAccountPort
import com.project.devlog.domain.account.application.usecase.AccountCalendarUseCase
import com.project.devlog.domain.account.exception.AccountNotFoundException
import com.project.devlog.domain.post.adapter.presentation.data.response.PostCalendarResponse
import com.project.devlog.domain.post.application.port.QueryPostPort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate
import java.util.*

class AccountCalendarUseCaseTest: BehaviorSpec({
    val queryAccountPort = mockk<QueryAccountPort>()
    val queryPostPort = mockk<QueryPostPort>()
    val accountCalendarUseCase = AccountCalendarUseCase(queryAccountPort, queryPostPort)

    // account
    val email = "test@test.com"
    val password = "test password"
    val name = "test name"

    Given("accountIdx가 주어질때") {
        val accountIdx = UUID.randomUUID()

        val accountDomain = Account(accountIdx, email, password, name, null, null, null, null, Authority.ROLE_ACCOUNT)
        val postCalendarResponse = mutableListOf(PostCalendarResponse(1, LocalDate.now()))

        every { queryAccountPort.queryAccountByIdx(accountIdx) } returns accountDomain
        every { queryPostPort.queryCountByOneYearAgo(accountDomain.idx) } returns postCalendarResponse

        When("달력 리스트 요청을 하면") {
            val result = accountCalendarUseCase.execute(accountIdx)

            Then("result와 postCalendarResponse가 같아야 한다.") {
                result shouldBe postCalendarResponse
            }
        }

        When("존재하지 않는 계정으로 요청을 하면") {
            every { queryAccountPort.queryAccountByIdx(accountIdx) } returns null

            Then("AccountNotFoundException이 터져야 한다.") {
                shouldThrow<AccountNotFoundException> {
                   accountCalendarUseCase.execute(accountIdx)
                }
            }
        }
    }

})