package com.project.devlog.domain.post.adapter.persistence.repository

import com.project.devlog.domain.post.adapter.persistence.entity.PostEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface PostRepository: CrudRepository<PostEntity, UUID> {

    fun findAllByOrderByCreatedAtDesc(): List<PostEntity>

}