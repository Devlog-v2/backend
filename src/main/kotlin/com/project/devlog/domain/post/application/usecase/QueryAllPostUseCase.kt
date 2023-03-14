package com.project.devlog.domain.post.application.usecase

import com.project.devlog.domain.account.application.port.QueryAccountPort
import com.project.devlog.domain.account.exception.AccountNotFoundException
import com.project.devlog.domain.like.application.port.QueryLikePort
import com.project.devlog.domain.post.adapter.presentation.data.response.PostListResponse
import com.project.devlog.domain.post.adapter.presentation.data.response.WriterResponse
import com.project.devlog.domain.post.application.port.QueryPostPort
import com.project.devlog.global.annotation.ReadOnlyUseCase
import org.springframework.data.domain.PageRequest
import java.util.UUID

@ReadOnlyUseCase
class QueryAllPostUseCase(
    private val queryPostPort: QueryPostPort,
    private val queryAccountPort: QueryAccountPort,
    private val queryLikePort: QueryLikePort
) {

    fun execute(page: Int, size: Int): List<PostListResponse> {
        val pageRequest = PageRequest.of(page, size)
        return queryPostPort.queryAllPost(pageRequest)
            .map {
                PostListResponse(
                    idx = it.idx,
                    title = it.title,
                    content = it.content,
                    writer = findAccountByIdx(it.accountIdx),
                    likeCount = queryLikePort.queryCountByPostIdx(it.idx),
                    images = it.images!!
                )
            }
    }

    private fun findAccountByIdx(idx: UUID): WriterResponse =
        queryAccountPort.queryAccountByIdx(idx)
            .let { it ?: throw AccountNotFoundException() }
            .let { WriterResponse(it.idx, it.name) }

}