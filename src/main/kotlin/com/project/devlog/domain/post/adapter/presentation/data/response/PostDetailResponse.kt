package com.project.devlog.domain.post.adapter.presentation.data.response

import com.project.devlog.domain.comment.adapter.presentation.data.response.CommentResponse
import com.project.devlog.domain.like.adapter.presentation.data.response.LikeResponse
import java.time.LocalDate
import java.util.UUID

data class PostDetailResponse(
    val idx: UUID,
    val title: String,
    val content: String,
    val writer: WriterResponse,
    val comment: List<CommentResponse>,
    val like: LikeResponse,
    val tag: List<String>,
    val thumbnailUrl: String?,
    val createdDate: LocalDate
)