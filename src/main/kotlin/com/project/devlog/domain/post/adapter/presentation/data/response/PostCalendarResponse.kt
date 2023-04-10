package com.project.devlog.domain.post.adapter.presentation.data.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class PostCalendarResponse(
    val postCount: Long,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH-mm-ss")
    val date: LocalDateTime
)