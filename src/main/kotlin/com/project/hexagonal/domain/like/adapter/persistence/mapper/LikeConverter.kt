package com.project.hexagonal.domain.like.adapter.persistence.mapper

import com.project.hexagonal.domain.account.adapter.persistence.repository.AccountRepository
import com.project.hexagonal.domain.account.exception.AccountNotFoundException
import com.project.hexagonal.domain.like.Like
import com.project.hexagonal.domain.like.adapter.persistence.entity.LikeEntity
import com.project.hexagonal.domain.post.adapter.persistence.repository.PostRepository
import com.project.hexagonal.domain.post.exception.PostNotFoundException
import org.springframework.stereotype.Component

@Component
class LikeConverter(
    private val accountRepository: AccountRepository,
    private val postRepository: PostRepository
) {

    fun toEntity(domain: Like): LikeEntity {
        val account = accountRepository.findByIdx(domain.accountIdx) ?: throw AccountNotFoundException()
        val post = postRepository.findByIdx(domain.postIdx) ?: throw PostNotFoundException()
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