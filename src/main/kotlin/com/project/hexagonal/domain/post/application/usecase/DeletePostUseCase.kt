package com.project.hexagonal.domain.post.application.usecase

import com.project.hexagonal.domain.post.application.port.CommandPostPort
import com.project.hexagonal.domain.post.application.port.QueryPostPort
import com.project.hexagonal.domain.post.exception.PostNotFoundException
import com.project.hexagonal.global.annotation.UseCase
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