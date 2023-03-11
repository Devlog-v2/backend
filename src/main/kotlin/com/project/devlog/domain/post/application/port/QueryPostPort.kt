package com.project.devlog.domain.post.application.port

import com.project.devlog.domain.post.Post
import org.springframework.data.domain.PageRequest
import java.util.UUID

interface QueryPostPort {

    fun queryPostById(postIdx: UUID): Post?
    fun queryAllPost(pageRequest: PageRequest): List<Post>

}