package com.project.devlog.domain.comment.usecase

import com.project.devlog.domain.comment.Comment
import com.project.devlog.domain.comment.adapter.presentation.data.request.UpdateCommentRequest
import com.project.devlog.domain.comment.application.port.CommandCommentPort
import com.project.devlog.domain.comment.application.port.QueryCommentPort
import com.project.devlog.domain.comment.application.usecase.UpdateCommentUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class UpdateCommentUseCaseTest: BehaviorSpec({
    val commandCommentPort = mockk<CommandCommentPort>()
    val queryCommentPort = mockk<QueryCommentPort>()
    val updateCommentUseCase = UpdateCommentUseCase(commandCommentPort, queryCommentPort)

    // account
    val accountIdx = UUID.randomUUID()

    // post
    val postIdx = UUID.randomUUID()

    // comment
    val comment = "update test comment"

    Given("commentIdx, UpdateCommentRequest가 주어졌을때") {
        val commentIdx = UUID.randomUUID()
        val request = UpdateCommentRequest(comment)
        val commentDomain = Comment(commentIdx, request.comment, accountIdx, postIdx)

        every { queryCommentPort.queryByCommentIdx(any()) } returns commentDomain
        every { commandCommentPort.saveComment(any()) } returns commentDomain

        When("댓글 수정 요청을 하면") {
            val result = updateCommentUseCase.execute(postIdx, request)

            Then("댓글이 수정 되어야 한다.") {
                verify(exactly = 1) { commandCommentPort.saveComment(any()) }
            }

            Then("댓글 idx는 result와 같아야 한다.") {
                commentIdx shouldBe result
            }
        }
    }
})