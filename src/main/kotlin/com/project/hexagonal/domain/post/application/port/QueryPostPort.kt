package com.project.hexagonal.domain.post.application.port

import com.project.hexagonal.domain.post.Post
import org.springframework.data.domain.PageRequest
import java.util.UUID

interface QueryPostPort {

    fun queryPostById(postIdx: UUID): Post?
    fun queryAllPost(pageRequest: PageRequest): List<Post>

}