package com.project.devlog.domain.comment

import java.util.UUID

data class Comment(
    val idx: Long,
    val comment: String,
    val accountIdx: UUID,
    val postIdx: UUID
)