package com.project.devlog.domain.account.adapter.presentation.data.response

import java.util.UUID

data class ProfileDetailResponse(
    val accountIdx: UUID,
    val email: String,
    val name: String,
    val profileUrl: String?,
    val company: String?,
    val githubUrl: String?,
    val readme: String?
)