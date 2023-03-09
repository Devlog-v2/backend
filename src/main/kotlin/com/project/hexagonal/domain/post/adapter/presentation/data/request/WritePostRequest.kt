package com.project.hexagonal.domain.post.adapter.presentation.data.request

data class WritePostRequest(
    val title: String,
    val content: String,
    val tag: MutableList<String>
)