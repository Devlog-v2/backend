package com.project.hexagonal.domain.post.application.usecase

import com.project.hexagonal.domain.account.application.port.AccountSecurityPort
import com.project.hexagonal.domain.account.application.port.QueryAccountPort
import com.project.hexagonal.domain.account.exception.AccountNotFoundException
import com.project.hexagonal.domain.like.adapter.presentation.data.response.LikeResponse
import com.project.hexagonal.domain.like.application.port.QueryLikePort
import com.project.hexagonal.domain.post.adapter.presentation.data.response.PostDetailResponse
import com.project.hexagonal.domain.post.adapter.presentation.data.response.WriterResponse
import com.project.hexagonal.domain.post.application.port.QueryPostPort
import com.project.hexagonal.domain.post.exception.PostNotFoundException
import com.project.hexagonal.global.annotation.ReadOnlyUseCase
import java.util.UUID

@ReadOnlyUseCase
class QueryPostDetatilUseCase(
    private val queryPostPort: QueryPostPort,
    private val queryLikePort: QueryLikePort,
    private val accountSecurityPort: AccountSecurityPort,
    private val queryAccountPort: QueryAccountPort
) {

    fun execute(postIdx: UUID): PostDetailResponse {
        val account = queryAccountPort.queryAccountByIdx(accountSecurityPort.getCurrentAccountIdx())
            ?: throw AccountNotFoundException()
        val post = queryPostPort.queryPostById(postIdx) ?: throw PostNotFoundException()
        val like = queryLikePort.queryLikeByAccountIdxAndPostIdx(account.idx, post.idx)
        return PostDetailResponse(
            idx = post.idx,
            title = post.title,
            content = post.content,
            writer = WriterResponse(account.idx, account.name),
            like = LikeResponse(like.isLiked, queryLikePort.queryLikeCountByPostIdx(post.idx)),
            tag = post.tag
        )
    }

}