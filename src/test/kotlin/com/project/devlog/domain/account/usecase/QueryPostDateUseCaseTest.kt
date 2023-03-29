package com.project.devlog.domain.account.usecase

import com.project.devlog.domain.account.Account
import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import com.project.devlog.domain.account.application.port.QueryAccountPort
import com.project.devlog.domain.account.application.usecase.QueryPostDateUseCase
import com.project.devlog.domain.account.exception.AccountNotFoundException
import com.project.devlog.domain.like.application.port.QueryLikePort
import com.project.devlog.domain.post.Post
import com.project.devlog.domain.post.adapter.presentation.data.response.PostListResponse
import com.project.devlog.domain.post.adapter.presentation.data.response.WriterResponse
import com.project.devlog.domain.post.application.port.QueryPostPort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate
import java.util.UUID

class QueryPostDateUseCaseTest: BehaviorSpec({
    val queryPostPort = mockk<QueryPostPort>()
    val queryAccountPort = mockk<QueryAccountPort>()
    val queryLikePort = mockk<QueryLikePort>()
    val queryPostDateUseCase = QueryPostDateUseCase(queryPostPort, queryAccountPort, queryLikePort)

    // account
    val email = "test@test.com"
    val password = "test password"
    val name = "test name"

    // post
    val postIdx = UUID.randomUUID()
    val title = "test title"
    val content = "test content"
    val tag = mutableListOf("test tag1", "test tag2")
    val thumbnailUrl = "test thumbnailUrl"
    val createdAt = LocalDate.now()

    Given("date와 accountIdx가 주어질때") {
        val date = LocalDate.now()
        val accountIdx = UUID.randomUUID()

        val accountDomain = Account(accountIdx, email, password, name, null, null, null, null, Authority.ROLE_ACCOUNT)
        val postDomain = Post(postIdx, title, content, accountIdx, tag, thumbnailUrl, createdAt)
        val postListResponse = mutableListOf(postDomain.let {
            PostListResponse(
                idx = it.idx,
                title = it.title,
                content = it.content,
                writer = WriterResponse(accountDomain.idx, accountDomain.name, false, null),
                likeCount = 1,
                thumbnailUrl = it.thumbnailUrl,
                createdDate = it.createdDate
            )
        })

        every { queryAccountPort.queryAccountByIdx(accountIdx) } returns accountDomain
        every { queryLikePort.queryCountByPostIdx(postIdx) } returns 1
        every { queryPostPort.queryPostByDateAndAccountIdx(date, accountIdx) } returns mutableListOf(postDomain)

        When("특정 날짜에 쓰여진 게시글 리스트를 요청하면") {
            val result = queryPostDateUseCase.execute(date, accountIdx)

            Then("result와 postListResponse는 같아야 한다.") {
                result shouldBe postListResponse
            }
        }

        When("존재하지 않는 계정으로 요청을 하면") {
            every { queryAccountPort.queryAccountByIdx(accountIdx) } returns null

            Then("AccountNotFoundException이 터져야 한다.") {
                shouldThrow<AccountNotFoundException> {
                    queryPostDateUseCase.execute(date, accountIdx)
                }
            }
        }
    }

})