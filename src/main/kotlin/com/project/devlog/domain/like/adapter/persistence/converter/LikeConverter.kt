package com.project.devlog.domain.like.adapter.persistence.converter

import com.project.devlog.domain.account.adapter.persistence.repository.AccountRepository
import com.project.devlog.domain.account.exception.AccountNotFoundException
import com.project.devlog.domain.like.Like
import com.project.devlog.domain.like.adapter.persistence.entity.LikeEntity
import com.project.devlog.domain.post.adapter.persistence.repository.PostRepository
import com.project.devlog.domain.post.exception.PostNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class LikeConverter(
    private val accountRepository: AccountRepository,
    private val postRepository: PostRepository
) {

    fun toEntity(domain: Like): LikeEntity {
        val account = accountRepository.findByIdOrNull(domain.accountIdx) ?: throw AccountNotFoundException()
        val post = postRepository.findByIdOrNull(domain.postIdx) ?: throw PostNotFoundException()
        return LikeEntity(
            idx = domain.idx,
            isLiked = domain.isLiked,
            account = account,
            post = post
        )
    }

    fun toDomain(entity: LikeEntity): Like =
        entity.let {
            Like(
                idx = it.idx,
                isLiked = it.isLiked,
                accountIdx = it.account.idx,
                postIdx = it.post.idx
            )
        }

}