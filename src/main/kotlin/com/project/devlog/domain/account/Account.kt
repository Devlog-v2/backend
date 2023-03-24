package com.project.devlog.domain.account

import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import java.util.UUID

data class Account(
    val idx: UUID,
    val email: String,
    val encodedPassword: String,
    var name: String,
    var githubUrl: String?,
    var profileUrl: String?,
    var company: String?,
    var readme: String?,
    val authority: Authority
)
