package com.project.devlog.domain.post.application.usecase

import com.project.devlog.domain.post.adapter.presentation.data.request.UpdatePostRequest
import com.project.devlog.domain.post.application.port.CommandPostPort
import com.project.devlog.domain.post.application.port.QueryPostPort
import com.project.devlog.domain.post.exception.PostNotFoundException
import com.project.devlog.global.annotation.UseCase
import com.project.devlog.infrastructure.s3.application.port.S3Port
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@UseCase
class UpdatePostUseCase(
    private val commandPostPort: CommandPostPort,
    private val queryPostPort: QueryPostPort,
    private val s3Port: S3Port
) {

    fun execute(postIdx: UUID, fileList: MutableList<MultipartFile>?, request: UpdatePostRequest) {
        queryPostPort.queryPostById(postIdx)
            .let { it ?: throw PostNotFoundException() }
            .let {
                val uploadImages = fileList?.let { s3Port.uploadFile(fileList, "post/") }
                commandPostPort.updatePost(
                    it.copy(title = request.title, content = request.content, tag = request.tag, images = uploadImages)
                )
            }
    }

}