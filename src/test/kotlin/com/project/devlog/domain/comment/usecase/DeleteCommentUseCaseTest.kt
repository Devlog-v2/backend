package com.project.devlog.domain.comment.usecase

import com.project.devlog.domain.comment.Comment
import com.project.devlog.domain.comment.application.port.CommandCommentPort
import com.project.devlog.domain.comment.application.port.QueryCommentPort
import com.project.devlog.domain.comment.application.usecase.DeleteCommentUseCase
import com.project.devlog.domain.comment.exception.CommentNotFountException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.UUID

class DeleteCommentUseCaseTest: BehaviorSpec({
    val commandCommentPort = mockk<CommandCommentPort>()
    val queryCommentPort = mockk<QueryCommentPort>()
    val deleteCommentUseCase = DeleteCommentUseCase(commandCommentPort, queryCommentPort)

    // account
    val accountIdx = UUID.randomUUID()

    // post
    val postIdx = UUID.randomUUID()

    // comment
    val comment = "test comment"

    Given("commentIdx가 주어질때") {
        val commentIdx = UUID.randomUUID()
        val commentDomain = Comment(commentIdx, comment, accountIdx, postIdx)

        every { queryCommentPort.queryByCommentIdx(commentIdx) } returns commentDomain
        every { commandCommentPort.deleteComment(commentDomain) } returns Unit

        When("댓글 삭제 요청을 하면") {
            deleteCommentUseCase.execute(commentIdx)

            Then("댓글이 삭제가 되야한다.") {
                verify(exactly = 1) { commandCommentPort.deleteComment(commentDomain) }
            }
        }

        When("삭제된 댓글 idx로 요청하면") {
            every { queryCommentPort.queryByCommentIdx(commentIdx) } returns null

            Then("CommentNotFoundException이 터져야 한다.") {
                shouldThrow<CommentNotFountException> {
                    deleteCommentUseCase.execute(commentIdx)
                }
            }
        }
    }
})