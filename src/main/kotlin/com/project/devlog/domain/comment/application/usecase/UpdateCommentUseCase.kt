package com.project.devlog.domain.comment.application.usecase

import com.project.devlog.domain.comment.adapter.presentation.data.request.UpdateCommentRequest
import com.project.devlog.domain.comment.application.port.CommandCommentPort
import com.project.devlog.domain.comment.application.port.QueryCommentPort
import com.project.devlog.domain.comment.exception.CommentNotFountException
import com.project.devlog.global.annotation.UseCase
import java.util.UUID

@UseCase
class UpdateCommentUseCase(
    private val commandCommentPort: CommandCommentPort,
    private val queryCommentPort: QueryCommentPort
) {

    fun execute(commentIdx: UUID, request: UpdateCommentRequest): UUID =
        queryCommentPort.queryByCommentIdx(commentIdx)
            .let { it ?: throw CommentNotFountException() }
            .let {
                commandCommentPort.saveComment(
                    it.copy(
                        comment = request.comment,
                    )
                )
            }.idx

}