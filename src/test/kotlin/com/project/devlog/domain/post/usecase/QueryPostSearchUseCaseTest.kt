package com.project.devlog.domain.post.usecase

import com.project.devlog.domain.account.Account
import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import com.project.devlog.domain.account.application.port.QueryAccountPort
import com.project.devlog.domain.like.application.port.QueryLikePort
import com.project.devlog.domain.post.Post
import com.project.devlog.domain.post.application.port.QueryPostPort
import com.project.devlog.domain.post.application.usecase.QueryPostSearchUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate
import java.util.*

class QueryPostSearchUseCaseTest: BehaviorSpec({
    val queryPostPort = mockk<QueryPostPort>()
    val queryAccountPort = mockk<QueryAccountPort>()
    val queryLikePort = mockk<QueryLikePort>()
    val queryPostSearchUseCase = QueryPostSearchUseCase(queryPostPort, queryAccountPort, queryLikePort)

    // account
    val accountIdx = UUID.randomUUID()
    val email = "test email"
    val name = "test name"
    val password = "test password"

    // post
    val postIdx = UUID.randomUUID()
    val title = "test title"
    val content = "test content"
    val tag = mutableListOf("test tag1", "test tag2")
    val images = mutableListOf("test image1", "test image2")
    val createdAt = LocalDate.now()

    Given("title이 주어질때") {
        val postDomain = Post(postIdx, title, content, accountIdx, tag, images, createdAt)
        val account = Account(accountIdx, email, password, name, Authority.ROLE_ACCOUNT)

        every { queryPostPort.querySearchByTitle(title) } returns listOf(postDomain)
        every { queryAccountPort.queryAccountByIdx(postDomain.accountIdx) } returns account
        every { queryLikePort.queryCountByPostIdx(postDomain.idx) } returns 1

        When("post search 요청을 하면") {
            val result = queryPostSearchUseCase.execute(title)

            Then("게시글 찾기 쿼리가 실행 되어야 한다.") {
                queryPostPort.querySearchByTitle(postDomain.title)
            }

            Then("result 값이 null이 아니여야 한다.") {
                result shouldNotBe null
            }
        }
    }
})