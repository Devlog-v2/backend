package com.project.hexagonal.domain.post.adapter.presentation.data.response

import com.project.hexagonal.domain.like.adapter.presentation.data.response.LikeResponse
import java.util.UUID

data class PostDetailResponse(
    val idx: UUID,
    val title: String,
    val content: String,
    val writer: WriterResponse,
    val like: LikeResponse,
    val tag: MutableList<String>,
)