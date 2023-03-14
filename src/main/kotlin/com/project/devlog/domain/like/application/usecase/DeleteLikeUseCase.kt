package com.project.devlog.domain.like.application.usecase

import com.project.devlog.domain.account.application.port.AccountSecurityPort
import com.project.devlog.domain.like.application.port.CommandLikePort
import com.project.devlog.domain.like.application.port.QueryLikePort
import com.project.devlog.domain.like.exception.LikeNotFoundException
import com.project.devlog.global.annotation.UseCase
import java.util.*

@UseCase
class DeleteLikeUseCase(
    private val commandLikePort: CommandLikePort,
    private val queryLikePort: QueryLikePort,
    private val accountSecurityPort: AccountSecurityPort
) {

    fun execute(postIdx: UUID) {
        val accountIdx = accountSecurityPort.getCurrentAccountIdx()
        val like = queryLikePort.queryByAccountIdxAndPostIdx(accountIdx!!, postIdx) ?: throw LikeNotFoundException()
        commandLikePort.deleteLike(like)
    }

}