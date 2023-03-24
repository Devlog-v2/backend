package com.project.devlog.domain.post.usecase

import com.project.devlog.domain.account.Account
import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import com.project.devlog.domain.account.application.port.AccountSecurityPort
import com.project.devlog.domain.account.application.port.QueryAccountPort
import com.project.devlog.domain.comment.Comment
import com.project.devlog.domain.comment.application.port.QueryCommentPort
import com.project.devlog.domain.like.application.port.QueryLikePort
import com.project.devlog.domain.post.Post
import com.project.devlog.domain.post.application.port.QueryPostPort
import com.project.devlog.domain.post.application.usecase.QueryPostDetatilUseCase
import com.project.devlog.domain.post.exception.PostNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate
import java.util.*

class QueryPostDeatilUseCaseTest: BehaviorSpec({
    val queryPostPort = mockk<QueryPostPort>()
    val queryLikePort = mockk<QueryLikePort>()
    val queryCommentPort= mockk<QueryCommentPort>()
    val queryAccountPort = mockk<QueryAccountPort>()
    val accountSecurityPort = mockk<AccountSecurityPort>()
    val queryPostDetailUseCase = QueryPostDetatilUseCase(queryPostPort, queryLikePort, queryCommentPort, queryAccountPort, accountSecurityPort)

    // account
    val accountIdx = UUID.randomUUID()
    val email = "test@test.com"
    val name = "test name"
    val password = "test password"

    // post
    val title = "test title"
    val content = "test content"
    val tag = mutableListOf("test tag1", "test tag2")
    val createdAt = LocalDate.now()

    // comment
    val commentIdx = UUID.randomUUID()
    val comment = "test comment"

    Given("postIdx가 주어질때") {
        val postIdx = UUID.randomUUID()
        val postDomain = Post(postIdx, title, content, accountIdx, tag, createdAt)
        val commentDomain = Comment(commentIdx, comment, accountIdx, postIdx)
        val account = Account(accountIdx, email, password, name, null, null, null, null, Authority.ROLE_ACCOUNT)

        every { accountSecurityPort.getCurrentAccountIdx() } returns null
        every { queryPostPort.queryPostById(postDomain.idx) } returns postDomain
        every { queryLikePort.queryCountByPostIdx(postDomain.idx) } returns 1
        every { queryLikePort.queryExistsByAccountIdxAndPostIdx(any(), postDomain.idx) } returns false
        every { queryCommentPort.queryByPostIdx(postDomain.idx) } returns listOf(commentDomain)
        every { queryAccountPort.queryAccountByIdx(postDomain.accountIdx) } returns account

        When("게시글 디테일 요청을 하면") {
            val result = queryPostDetailUseCase.execute(postDomain.idx)

            Then("게시글 디테일 쿼리가 실행 되어야 한다.") {
                queryPostPort.queryPostById(postDomain.idx)
            }

            Then("result 값이 null이 아니여야 한다.") {
                result shouldNotBe null
            }
        }

        When("삭제된 게시글 idx를 요청하면") {
            every { queryPostPort.queryPostById(postIdx) } returns null

            Then("PostNotFoundException이 터져야 한다.") {
                shouldThrow<PostNotFoundException> {
                    queryPostDetailUseCase.execute(postIdx)
                }
            }
        }
    }
})