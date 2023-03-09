package com.project.hexagonal.domain.comment.adapter.persistence.repository

import com.project.hexagonal.domain.comment.adapter.persistence.entity.CommentEntity
import org.springframework.data.repository.CrudRepository

interface CommentRepository: CrudRepository<CommentEntity, Long>