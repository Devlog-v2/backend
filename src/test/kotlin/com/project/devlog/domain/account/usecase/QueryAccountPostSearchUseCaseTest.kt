package com.project.devlog.domain.account.usecase

import com.project.devlog.domain.account.Account
import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import com.project.devlog.domain.account.application.port.QueryAccountPort
import com.project.devlog.domain.account.application.usecase.QueryAccountPostSearchUseCase
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
import java.time.LocalDateTime
import java.util.UUID

class QueryAccountPostSearchUseCaseTest : BehaviorSpec({
    val queryAccountPort = mockk<QueryAccountPort>()
    val queryPostPort = mockk<QueryPostPort>()
    val queryLikePort = mockk<QueryLikePort>()
    val queryAccountPostSearchUseCase = QueryAccountPostSearchUseCase(queryAccountPort, queryPostPort, queryLikePort)

    // account
    val email = "test@test.com"
    val password = "test password"
    val name = "test name"

    // post
    val postIdx = UUID.randomUUID()
    val content = "test content"
    val tag = mutableListOf("test tag1", "test tag2")
    val thumbnailUrl = "test thumbnailUrl"
    val createdAt = LocalDateTime.now()

    Given("accountIdx와 title이 주어질때") {
        val accountIdx = UUID.randomUUID()
        val title = "test title"

        val accountDomain = Account(accountIdx, email, password, name, null, null, null, null, null, Authority.ROLE_ACCOUNT)
        val postDomain = Post(postIdx, title, content, accountIdx, tag, thumbnailUrl, createdAt)
        val postListResponse = mutableListOf(postDomain.let {
            PostListResponse(
                idx = it.idx,
                title = it.title,
                content = it.content,
                writer = WriterResponse(accountDomain.idx, accountDomain.name, false, null),
                likeCount = 1,
                thumbnailUrl = it.thumbnailUrl,
                createdDate = it.createdDate.toLocalDate()
            )
        })

        every { queryAccountPort.queryAccountByIdx(accountIdx) } returns accountDomain
        every { queryPostPort.querySearchPostByAccountIdx(accountIdx, title) } returns mutableListOf(postDomain)
        every { queryLikePort.queryCountByPostIdx(postDomain.idx) } returns 1

        When("계정 게시글 검색을 요청 하면") {
            val result = queryAccountPostSearchUseCase.execute(accountIdx, title)

            Then("result와 postListResponse가 같아야 한다.") {
                result shouldBe postListResponse
            }
        }

        When("존재하지 않는 계정으로 요청을 하면") {
            every { queryAccountPort.queryAccountByIdx(accountIdx) } returns null

            Then("AccountNotFoundException이 터져야 한다.") {
                shouldThrow<AccountNotFoundException> {
                    queryAccountPostSearchUseCase.execute(accountIdx, title)
                }
            }
        }
    }
})