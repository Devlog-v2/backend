package com.project.hexagonal.domain.comment

data class Comment(
    val idx: Long,
    val comment: String,
    val accountIdx: Long,
    val postIdx: Long
)