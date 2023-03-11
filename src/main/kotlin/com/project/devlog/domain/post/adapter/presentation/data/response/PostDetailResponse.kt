package com.project.devlog.domain.post.adapter.presentation.data.response

import com.project.devlog.domain.like.adapter.presentation.data.response.LikeResponse
import java.util.UUID

data class PostDetailResponse(
    val idx: UUID,
    val title: String,
    val content: String,
    val writer: WriterResponse,
    val like: LikeResponse,
    val tag: MutableList<String>,
)