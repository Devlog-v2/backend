package com.project.devlog.domain.post.application.usecase

import com.project.devlog.domain.account.application.port.AccountSecurityPort
import com.project.devlog.domain.account.application.port.QueryAccountPort
import com.project.devlog.domain.comment.adapter.presentation.data.response.CommentResponse
import com.project.devlog.domain.comment.application.port.QueryCommentPort
import com.project.devlog.domain.like.adapter.presentation.data.response.LikeResponse
import com.project.devlog.domain.like.application.port.QueryLikePort
import com.project.devlog.domain.post.adapter.presentation.data.response.PostDetailResponse
import com.project.devlog.domain.post.adapter.presentation.data.response.WriterResponse
import com.project.devlog.domain.post.application.port.QueryPostPort
import com.project.devlog.domain.post.exception.PostNotFoundException
import com.project.devlog.global.annotation.ReadOnlyUseCase
import java.util.*
import javax.security.auth.login.AccountNotFoundException

@ReadOnlyUseCase
class QueryPostDetatilUseCase(
    private val queryPostPort: QueryPostPort,
    private val queryLikePort: QueryLikePort,
    private val queryCommentPort: QueryCommentPort,
    private val queryAccountPort: QueryAccountPort,
    private val accountSecurityPort: AccountSecurityPort
) {

    fun execute(postIdx: UUID): PostDetailResponse {
        val post = queryPostPort.queryPostById(postIdx) ?: throw PostNotFoundException()
        val isLiked = queryLikePort.queryExistsByAccountIdxAndPostIdx(accountSecurityPort.getCurrentAccountIdx(), post.idx)
        val comment = queryCommentPort.queryByPostIdx(post.idx)

        return PostDetailResponse(
            idx = post.idx,
            title = post.title,
            content = post.content,
            writer = accountIdxToWriterResponse(post.accountIdx),
            comment =
                comment.map {
                    CommentResponse(
                        it.idx,
                        accountIdxToWriterResponse(it.accountIdx),
                        it.comment
                    )
                },
            like = LikeResponse(isLiked, queryLikePort.queryCountByPostIdx(post.idx)),
            tag = post.tag,
            createdDate = post.createdDate
        )
    }

    private fun accountIdxToWriterResponse(accountIdx: UUID): WriterResponse =
        queryAccountPort.queryAccountByIdx(accountIdx)
            .let { it ?: throw AccountNotFoundException() }
            .let {
                WriterResponse(
                    it.idx,
                    it.name,
                    it.idx == accountSecurityPort.getCurrentAccountIdx(),
                    it.profileUrl
                )
            }

}