package com.project.hexagonal.domain.post.adapter.persistence.converter

import com.project.hexagonal.domain.account.adapter.persistence.repository.AccountRepository
import com.project.hexagonal.domain.account.exception.AccountNotFoundException
import com.project.hexagonal.domain.post.Post
import com.project.hexagonal.domain.post.adapter.persistence.entity.PostEntity
import org.springframework.stereotype.Component

@Component
class PostConverter(
    private val accountRepository: AccountRepository
) {

    fun toEntity(domain: Post): PostEntity {
        val account = accountRepository.findByIdx(domain.accountIdx) ?: throw AccountNotFoundException()
        return domain.let {
            PostEntity(
                idx = it.idx,
                title = it.title,
                content = it.content,
                account = account,
                tag = it.tag
            )
        }
    }

    fun toDomain(entity: PostEntity): Post =
        entity.let {
            Post(
                idx = it.idx,
                title = it.title,
                content = it.content,
                accountIdx = it.account.idx,
                tag = it.tag
            )
        }
}