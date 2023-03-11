package com.project.devlog.domain.like

import java.util.UUID

data class Like(
    val idx: Long,
    val isLiked: Boolean,
    val accountIdx: UUID,
    val postIdx: UUID
)
