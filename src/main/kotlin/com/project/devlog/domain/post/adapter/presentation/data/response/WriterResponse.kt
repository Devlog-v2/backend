package com.project.devlog.domain.post.adapter.presentation.data.response

import java.util.UUID

data class WriterResponse(
    val accountIdx: UUID,
    val name: String,
    val isMine: Boolean
)