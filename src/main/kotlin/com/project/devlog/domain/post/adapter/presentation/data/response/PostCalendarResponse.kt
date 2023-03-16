package com.project.devlog.domain.post.adapter.presentation.data.response

import java.time.LocalDate

data class PostCalendarResponse(
    val postCount: Long,
    val date: LocalDate
)