package com.project.hexagonal.domain.like.application.usecase

import com.project.hexagonal.domain.account.application.port.AccountSecurityPort
import com.project.hexagonal.domain.like.application.port.CommandLikePort
import com.project.hexagonal.domain.like.application.port.QueryLikePort
import com.project.hexagonal.domain.like.exception.LikeNotFoundException
import com.project.hexagonal.global.annotation.UseCase
import java.util.*

@UseCase
class DeleteLikeUseCase(
    private val commandLikePort: CommandLikePort,
    private val queryLikePort: QueryLikePort,
    private val accountSecurityPort: AccountSecurityPort
) {

    fun execute(postIdx: UUID) {
        val accountIdx = accountSecurityPort.getCurrentAccountIdx()
        val like = queryLikePort.queryLikeByAccountIdxAndPostIdx(accountIdx, postIdx) ?: throw LikeNotFoundException()
        commandLikePort.deleteLike(like)
    }

}