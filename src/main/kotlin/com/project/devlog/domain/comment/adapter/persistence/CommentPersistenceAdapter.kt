package com.project.devlog.domain.comment.adapter.persistence

import com.project.devlog.domain.comment.Comment
import com.project.devlog.domain.comment.adapter.persistence.converter.CommentConverter
import com.project.devlog.domain.comment.adapter.persistence.entity.toUpdate
import com.project.devlog.domain.comment.adapter.persistence.repository.CommentRepository
import com.project.devlog.domain.comment.application.port.CommentPort
import com.project.devlog.global.annotation.AdapterWithTransaction
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@AdapterWithTransaction
class CommentPersistenceAdapter(
    private val commentRepository: CommentRepository,
    private val commentConverter: CommentConverter
): CommentPort {

    override fun saveComment(domain: Comment): Comment =
        commentConverter.toDomain(commentRepository.save(commentConverter.toEntity(domain)))

    override fun updateComment(domain: Comment) =
        commentConverter.toEntity(domain).toUpdate(domain.comment)

    override fun deleteComment(domain: Comment) =
        commentRepository.delete(commentConverter.toEntity(domain))

    override fun queryByCommentIdx(commentIdx: UUID): Comment? =
        commentRepository.findByIdOrNull(commentIdx)?.let { commentConverter.toDomain(it) }

    override fun queryExsistByCommentIdxAndAccountIdx(commentIdx: UUID, postIdx: UUID): Boolean =
        commentRepository.existsByIdxAndAccountIdx(commentIdx, postIdx)

    override fun queryByPostIdx(postIdx: UUID): List<Comment> =
        commentRepository.findByPostIdx(postIdx).map { commentConverter.toDomain(it) }

}