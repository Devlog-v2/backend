package com.project.devlog.domain.post

import java.util.UUID

data class Post(
    val idx: UUID,
    val title: String,
    val content: String,
    val accountIdx: UUID,
    val tag: List<String>,
    val images: List<String>
)
