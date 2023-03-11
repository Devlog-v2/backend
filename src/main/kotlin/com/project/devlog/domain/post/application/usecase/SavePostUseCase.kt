package com.project.devlog.domain.post.application.usecase

import com.project.devlog.domain.account.application.port.AccountSecurityPort
import com.project.devlog.domain.post.Post
import com.project.devlog.domain.post.adapter.presentation.data.request.WritePostRequest
import com.project.devlog.domain.post.application.port.CommandPostPort
import com.project.devlog.global.annotation.UseCase
import java.util.UUID

@UseCase
class SavePostUseCase(
    private val commandPostPort: CommandPostPort,
    private val accountSecurityPort: AccountSecurityPort,
) {

    fun execute(requst: WritePostRequest): UUID {
        val post = requst.let {
            Post(
                idx = UUID.randomUUID(),
                title = it.title,
                content = it.content,
                accountIdx = accountSecurityPort.getCurrentAccountIdx(),
                tag = it.tag
            )
        }
        return commandPostPort.savePost(post).idx
    }

}