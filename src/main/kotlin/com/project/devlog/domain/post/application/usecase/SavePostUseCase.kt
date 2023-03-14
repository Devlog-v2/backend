package com.project.devlog.domain.post.application.usecase

import com.project.devlog.domain.account.application.port.AccountSecurityPort
import com.project.devlog.domain.post.Post
import com.project.devlog.domain.post.adapter.presentation.data.request.WritePostRequest
import com.project.devlog.domain.post.application.port.CommandPostPort
import com.project.devlog.global.annotation.UseCase
import com.project.devlog.infrastructure.s3.application.port.S3UploadPort
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@UseCase
class SavePostUseCase(
    private val commandPostPort: CommandPostPort,
    private val accountSecurityPort: AccountSecurityPort,
    private val s3UploadPort: S3UploadPort
) {

    fun execute(fileList: MutableList<MultipartFile>?, requst: WritePostRequest): UUID {
        val uploadImages = fileList?.let { s3UploadPort.uploadFile(it, "post/") }
        val post = requst.let {
            Post(
                idx = UUID.randomUUID(),
                title = it.title,
                content = it.content,
                accountIdx = accountSecurityPort.getCurrentAccountIdx(),
                tag = it.tag,
                images = uploadImages
            )
        }
        return commandPostPort.savePost(post).idx
    }

}