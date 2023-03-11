package com.project.devlog.domain.post.application.usecase

import com.project.devlog.domain.post.adapter.presentation.data.request.UpdatePostRequest
import com.project.devlog.domain.post.application.port.CommandPostPort
import com.project.devlog.domain.post.application.port.QueryPostPort
import com.project.devlog.domain.post.exception.PostNotFoundException
import com.project.devlog.global.annotation.UseCase
import java.util.UUID

@UseCase
class UpdatePostUseCase(
    private val commandPostPort: CommandPostPort,
    private val queryPostPort: QueryPostPort
) {

    fun execute(postIdx: UUID, request: UpdatePostRequest) =
        queryPostPort.queryPostById(postIdx)
            .let { it ?: throw PostNotFoundException() }
            .let {
                commandPostPort.updatePost(
                    it.copy(title = request.title, content = request.content, tag = request.tag)
                )
            }

}