package com.project.hexagonal.domain.post.application.usecase

import com.project.hexagonal.domain.account.application.port.AccountSecurityPort
import com.project.hexagonal.domain.post.Post
import com.project.hexagonal.domain.post.adapter.presentation.data.request.WritePostRequest
import com.project.hexagonal.domain.post.application.port.CommandPostPort
import com.project.hexagonal.global.annotation.UseCase
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