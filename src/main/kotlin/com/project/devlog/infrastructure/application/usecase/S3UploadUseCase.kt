package com.project.devlog.infrastructure.application.usecase

import com.project.devlog.global.annotation.UseCase
import com.project.devlog.infrastructure.adapter.presentation.data.response.ImageUrlResponse
import com.project.devlog.infrastructure.application.port.S3UploadPort
import org.springframework.web.multipart.MultipartFile

@UseCase
class S3UploadUseCase(
    private val s3UploadPort: S3UploadPort
) {

    fun execute(image: MultipartFile): ImageUrlResponse =
        ImageUrlResponse(s3UploadPort.uploadImage(image))

}