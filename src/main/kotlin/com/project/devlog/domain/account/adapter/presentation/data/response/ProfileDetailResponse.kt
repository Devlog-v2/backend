package com.project.devlog.domain.account.adapter.presentation.data.response

import java.util.UUID

data class ProfileDetailResponse(
    val accountIdx: UUID,
    val email: String,
    val name: String,
    val profileUrl: String?,
    val githubUrl: String?,
    val service: MutableList<String>?,
    val company: String?,
    val readme: String?,
    val isMine: Boolean
)