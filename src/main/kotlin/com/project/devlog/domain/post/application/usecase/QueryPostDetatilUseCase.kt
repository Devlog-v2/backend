package com.project.devlog.domain.post.application.usecase

import com.project.devlog.domain.account.application.port.QueryAccountPort
import com.project.devlog.domain.account.exception.AccountNotFoundException
import com.project.devlog.domain.like.adapter.presentation.data.response.LikeResponse
import com.project.devlog.domain.like.application.port.QueryLikePort
import com.project.devlog.domain.post.adapter.presentation.data.response.PostDetailResponse
import com.project.devlog.domain.post.adapter.presentation.data.response.WriterResponse
import com.project.devlog.domain.post.application.port.QueryPostPort
import com.project.devlog.domain.post.exception.PostNotFoundException
import com.project.devlog.global.annotation.ReadOnlyUseCase
import java.util.*

@ReadOnlyUseCase
class QueryPostDetatilUseCase(
    private val queryPostPort: QueryPostPort,
    private val queryLikePort: QueryLikePort,
    private val queryAccountPort: QueryAccountPort
) {

    fun execute(postIdx: UUID): PostDetailResponse {

        val post = queryPostPort.queryPostById(postIdx) ?: throw PostNotFoundException()
        val account = queryAccountPort.queryAccountByIdx(post.accountIdx) ?: throw AccountNotFoundException()
        val isLiked = queryLikePort.queryExistsByAccountIdxAndPostIdx(account.idx, post.idx)

        return PostDetailResponse(
            idx = post.idx,
            title = post.title,
            content = post.content,
            writer = WriterResponse(account.idx, account.name),
            like = LikeResponse(isLiked, queryLikePort.queryCountByPostIdx(post.idx)),
            tag = post.tag,
            images = post.images!!
        )
    }

}