package com.project.devlog.domain.comment.adapter.persistence.repository

import com.project.devlog.domain.comment.adapter.persistence.entity.CommentEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface CommentRepository: CrudRepository<CommentEntity, UUID> {

    fun findByPostIdx(postIdx: UUID): List<CommentEntity>
    fun existsByIdxAndAccountIdx(commentIdx: UUID, postIdx: UUID): Boolean

}