package com.project.devlog.domain.post.adapter.persistence.repository

import com.project.devlog.domain.post.adapter.presentation.data.response.PostCalendarResponse
import java.util.UUID

interface PostRepositoryCustom {

    fun countByOneYearAgo(accountIdx: UUID): List<PostCalendarResponse>

}