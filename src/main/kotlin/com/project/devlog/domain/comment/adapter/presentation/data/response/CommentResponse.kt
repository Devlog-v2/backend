package com.project.devlog.domain.comment.adapter.presentation.data.response

import com.project.devlog.domain.post.adapter.presentation.data.response.WriterResponse
import java.util.UUID

data class CommentResponse(
    val commentIdx: UUID,
    val writer: WriterResponse,
    val comment: String,
)