package com.project.devlog.domain.comment.adapter.persistence.converter

import com.project.devlog.domain.account.adapter.persistence.repository.AccountRepository
import com.project.devlog.domain.account.exception.AccountNotFoundException
import com.project.devlog.domain.comment.Comment
import com.project.devlog.domain.comment.adapter.persistence.entity.CommentEntity
import com.project.devlog.domain.post.adapter.persistence.repository.PostRepository
import com.project.devlog.domain.post.exception.PostNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class CommentConverter(
    private val postRepository: PostRepository,
    private val accountRepository: AccountRepository
) {

    fun toEntity(domain: Comment): CommentEntity {
        val post = postRepository.findByIdOrNull(domain.postIdx) ?: throw PostNotFoundException()
        val account = accountRepository.findByIdOrNull(domain.accountIdx) ?: throw AccountNotFoundException()
        return domain.let {
            CommentEntity(
                idx = it.idx,
                comment = it.comment,
                account = account,
                post = post
                )
        }
    }

    fun toDomain(entity: CommentEntity): Comment =
        entity.let {
            Comment(
                idx = it.idx,
                comment = it.comment,
                accountIdx = entity.account.idx,
                postIdx = entity.post.idx
            )
        }

}