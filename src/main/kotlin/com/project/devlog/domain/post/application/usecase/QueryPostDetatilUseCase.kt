package com.project.devlog.domain.post.application.usecase

import com.project.devlog.domain.account.application.port.AccountSecurityPort
import com.project.devlog.domain.account.application.port.QueryAccountPort
import com.project.devlog.domain.account.exception.AccountNotFoundException
import com.project.devlog.domain.like.adapter.presentation.data.response.LikeResponse
import com.project.devlog.domain.like.application.port.QueryLikePort
import com.project.devlog.domain.like.exception.LikeNotFoundException
import com.project.devlog.domain.post.adapter.presentation.data.response.PostDetailResponse
import com.project.devlog.domain.post.adapter.presentation.data.response.WriterResponse
import com.project.devlog.domain.post.application.port.QueryPostPort
import com.project.devlog.domain.post.exception.PostNotFoundException
import com.project.devlog.global.annotation.ReadOnlyUseCase
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
        val isLike = queryLikePort.queryLikeByAccountIdxAndPostIdx(account.idx, post.idx) ?: throw LikeNotFoundException()
        return PostDetailResponse(
            idx = post.idx,
            title = post.title,
            content = post.content,
            writer = WriterResponse(account.idx, account.name),
            like = LikeResponse(isLike.isLiked, queryLikePort.queryLikeCountByPostIdx(post.idx)),
            tag = post.tag
        )
    }

}