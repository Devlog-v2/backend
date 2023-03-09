package com.project.hexagonal.domain.post

import java.util.UUID

data class Post(
    val idx: UUID,
    val title: String,
    val content: String,
    val accountIdx: UUID,
    val tag: MutableList<String>,
)
