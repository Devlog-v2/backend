package com.project.hexagonal.domain.comment.adapter.presentation.data.response

import java.util.UUID

class CommentResponse(
    val idx: UUID,
    val comment: String,
    val isMine: Boolean
)