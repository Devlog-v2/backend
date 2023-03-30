package com.project.devlog.domain.post

import java.time.LocalDateTime
import java.util.*

data class Post(
    val idx: UUID,
    val title: String,
    val content: String,
    val accountIdx: UUID,
    val tag: List<String>,
    val thumbnailUrl: String?,
    val createdDate: LocalDateTime,
)
