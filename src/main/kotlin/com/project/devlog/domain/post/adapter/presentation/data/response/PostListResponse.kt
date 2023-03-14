package com.project.devlog.domain.post.adapter.presentation.data.response

import java.util.UUID

data class PostListResponse(
    val idx: UUID,
    val title: String,
    val content: String,
    val writer: WriterResponse,
    val likeCount: Int,
    val images: List<String>
)