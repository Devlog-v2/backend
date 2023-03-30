package com.project.devlog.domain.post.adapter.persistence.repository

import com.project.devlog.domain.post.adapter.persistence.entity.PostEntity
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime
import java.util.*

interface PostRepository: CrudRepository<PostEntity, UUID> {

    fun findAllByOrderByCreatedAtDesc(): List<PostEntity>
    fun findByTitleContainingOrderByCreatedAtDesc(title: String): List<PostEntity>
    fun findByAccountIdxOrderByCreatedAtDesc(accountIdx: UUID): List<PostEntity>
    fun findByAccountIdxAndTitleContainingOrderByCreatedAtDesc(accountIdx: UUID, title: String): List<PostEntity>
    fun findByCreatedAtAndAccountIdx(date: LocalDateTime, accountIdx: UUID): List<PostEntity>

}