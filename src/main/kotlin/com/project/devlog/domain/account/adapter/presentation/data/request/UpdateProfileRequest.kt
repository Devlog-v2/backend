package com.project.devlog.domain.account.adapter.presentation.data.request

data class UpdateProfileRequest(
    val name: String,
    val profileUrl: String?,
    val company: String?,
    val githubUrl: String?,
    val readme: String?
)