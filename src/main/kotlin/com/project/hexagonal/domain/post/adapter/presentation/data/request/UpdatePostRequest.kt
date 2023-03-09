package com.project.hexagonal.domain.post.adapter.presentation.data.request

data class UpdatePostRequest(
    val title: String,
    val content: String,
    val tag: MutableList<String>
)