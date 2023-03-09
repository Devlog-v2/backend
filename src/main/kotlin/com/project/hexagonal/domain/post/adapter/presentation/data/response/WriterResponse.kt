package com.project.hexagonal.domain.post.adapter.presentation.data.response

import java.util.UUID

data class WriterResponse(
    val accountIdx: UUID,
    val name: String,
)