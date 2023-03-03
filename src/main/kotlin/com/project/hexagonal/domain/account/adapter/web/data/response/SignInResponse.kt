package com.project.hexagonal.domain.account.adapter.web.data.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class SignInResponse(
    val accessToken: String,
    val refreshToken: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH-mm-ss")
    val accessTokenExpiredAt: LocalDateTime
)