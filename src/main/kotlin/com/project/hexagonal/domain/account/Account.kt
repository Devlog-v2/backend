package com.project.hexagonal.domain.account

import java.util.UUID

data class Account(
    val idx: UUID,
    val email: String,
    val encodedPassword: String,
    val name: String,
)
