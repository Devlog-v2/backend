package com.project.hexagonal.domain.comment.adapter.persistence

import com.project.hexagonal.domain.comment.Comment
import com.project.hexagonal.domain.comment.adapter.persistence.converter.CommentConverter
import com.project.hexagonal.domain.comment.adapter.persistence.repository.CommentRepository
import com.project.hexagonal.domain.comment.adapter.presentation.data.response.CommentResponse
import com.project.hexagonal.domain.comment.application.port.CommentPort
import com.project.hexagonal.global.annotation.AdapterWithTransaction
import java.util.*

@AdapterWithTransaction
class CommentPersistenceAdapter(
    private val commentRepository: CommentRepository,
    private val commentConverter: CommentConverter
): CommentPort {

    override fun saveComment(domain: Comment): Comment =
        commentConverter.toDomain(commentRepository.save(commentConverter.toEntity(domain)))

    override fun deleteComment(domain: Comment) {
        TODO("Not yet implemented")
    }

    override fun queryCommentByPostIdx(postIdx: UUID): List<CommentResponse> {
        TODO("Not yet implemented")
    }

    override fun queryExsistCommentByIdxAndAccountIdx(commentIdx: UUID, postIdx: UUID): Boolean {
        TODO("Not yet implemented")
    }

}