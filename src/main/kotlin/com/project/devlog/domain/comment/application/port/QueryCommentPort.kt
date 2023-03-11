package com.project.devlog.domain.comment.application.port

import com.project.devlog.domain.comment.adapter.presentation.data.response.CommentResponse
import java.util.UUID

interface QueryCommentPort {

    fun queryCommentByPostIdx(postIdx: UUID): List<CommentResponse>
    fun queryExsistCommentByIdxAndAccountIdx(commentIdx: UUID, postIdx: UUID): Boolean

}