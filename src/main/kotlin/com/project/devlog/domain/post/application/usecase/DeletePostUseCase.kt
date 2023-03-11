package com.project.devlog.domain.post.application.usecase

import com.project.devlog.domain.post.application.port.CommandPostPort
import com.project.devlog.domain.post.application.port.QueryPostPort
import com.project.devlog.domain.post.exception.PostNotFoundException
import com.project.devlog.global.annotation.UseCase
import java.util.UUID

@UseCase
class DeletePostUseCase(
    private val commandPostPort: CommandPostPort,
    private val queryPostPort: QueryPostPort
) {

    fun execute(postIdx: UUID) =
        queryPostPort.queryPostById(postIdx)
            .let { it ?: throw PostNotFoundException() }
            .let { commandPostPort.deletePost(it) }

}