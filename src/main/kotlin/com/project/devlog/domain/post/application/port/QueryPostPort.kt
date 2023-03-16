package com.project.devlog.domain.post.application.port

import com.project.devlog.domain.post.Post
import com.project.devlog.domain.post.adapter.presentation.data.response.PostCalendarResponse
import java.util.UUID

interface QueryPostPort {

    fun queryPostById(postIdx: UUID): Post?
    fun queryAllPost(): List<Post>
    fun queryCountByOneYearAgo(accountIdx: UUID): List<PostCalendarResponse>
    fun querySearchByTitle(title: String): List<Post>

}