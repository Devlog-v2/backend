package com.project.devlog.domain.like.application.usecase

import com.project.devlog.domain.account.application.port.AccountSecurityPort
import com.project.devlog.domain.like.Like
import com.project.devlog.domain.like.application.port.CommandLikePort
import com.project.devlog.global.annotation.UseCase
import java.util.*

@UseCase
class SaveLikeUseCase(
    private val commandLikePort: CommandLikePort,
    private val accountSecurityPort: AccountSecurityPort
) {

    fun execute(postIdx: UUID): Long {
        val accountIdx = accountSecurityPort.getCurrentAccountIdx()
        val like = Like(idx = -1, isLiked = true, accountIdx = accountIdx!!, postIdx = postIdx)
        return commandLikePort.saveLike(like).idx
    }

}