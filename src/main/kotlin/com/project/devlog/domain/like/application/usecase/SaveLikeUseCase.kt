package com.project.devlog.domain.like.application.usecase

import com.project.devlog.domain.account.application.port.AccountSecurityPort
import com.project.devlog.domain.like.Like
import com.project.devlog.domain.like.application.port.CommandLikePort
import com.project.devlog.domain.like.application.port.QueryLikePort
import com.project.devlog.domain.like.exception.DuplicateLikeException
import com.project.devlog.global.annotation.UseCase
import java.util.*

@UseCase
class SaveLikeUseCase(
    private val commandLikePort: CommandLikePort,
    private val queryLikePort: QueryLikePort,
    private val accountSecurityPort: AccountSecurityPort
) {

    fun execute(postIdx: UUID): Long {
        val accountIdx = accountSecurityPort.getCurrentAccountIdx()
        val like = Like(idx = -1, isLiked = true, accountIdx = accountIdx!!, postIdx = postIdx)
        if (queryLikePort.queryExistsByAccountIdxAndPostIdx(accountIdx, postIdx)) {
            throw DuplicateLikeException()
        }
        return commandLikePort.saveLike(like).idx
    }

}