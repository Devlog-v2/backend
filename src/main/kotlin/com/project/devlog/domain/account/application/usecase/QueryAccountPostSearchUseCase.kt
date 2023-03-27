package com.project.devlog.domain.account.application.usecase

import com.project.devlog.domain.account.application.port.QueryAccountPort
import com.project.devlog.domain.account.exception.AccountNotFoundException
import com.project.devlog.domain.like.application.port.QueryLikePort
import com.project.devlog.domain.post.adapter.presentation.data.response.PostListResponse
import com.project.devlog.domain.post.adapter.presentation.data.response.WriterResponse
import com.project.devlog.domain.post.application.port.QueryPostPort
import com.project.devlog.global.annotation.ReadOnlyUseCase
import java.util.*

@ReadOnlyUseCase
class QueryAccountPostSearchUseCase(
    private val queryAccountPort: QueryAccountPort,
    private val queryPostPort: QueryPostPort,
    private val queryLikePort: QueryLikePort
) {

    fun execute(accountIdx: UUID, title: String): List<PostListResponse> {
        val account = queryAccountPort.queryAccountByIdx(accountIdx)
            ?: throw AccountNotFoundException()
        val postList = queryPostPort.querySearchPostByAccountIdx(accountIdx, title)
        return postList.map {
            PostListResponse(
                idx = it.idx,
                title = it.title,
                content = it.content,
                writer = WriterResponse(account.idx, account.name, false, account.profileUrl),
                likeCount = queryLikePort.queryCountByPostIdx(it.idx),
                createdDate = it.createdDate
            )
        }
    }

}