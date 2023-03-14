package com.project.devlog.domain.comment.application.usecase

import com.project.devlog.domain.account.application.port.AccountSecurityPort
import com.project.devlog.domain.comment.Comment
import com.project.devlog.domain.comment.adapter.presentation.data.request.WriteCommentRequest
import com.project.devlog.domain.comment.application.port.CommandCommentPort
import com.project.devlog.global.annotation.UseCase
import java.util.*

@UseCase
class SaveCommentUseCase(
    private val commandCommentPort: CommandCommentPort,
    private val accountSecurityPort: AccountSecurityPort
) {

    fun execute(postIdx: UUID, request: WriteCommentRequest): UUID {
        val comment = Comment(
            idx = UUID.randomUUID(),
            comment = request.comment,
            postIdx = postIdx,
            accountIdx = accountSecurityPort.getCurrentAccountIdx()!!,
        )
        return commandCommentPort.saveComment(comment).idx
    }

}