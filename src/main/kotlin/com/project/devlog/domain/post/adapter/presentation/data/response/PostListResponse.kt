package com.project.devlog.domain.post.adapter.presentation.data.response

import java.time.LocalDate
import java.util.*

data class PostListResponse(
    val idx: UUID,
    val title: String,
    val content: String,
    val writer: WriterResponse,
    val likeCount: Int,
    val thumbnailUrl: String?,
    val createdDate: LocalDate
)