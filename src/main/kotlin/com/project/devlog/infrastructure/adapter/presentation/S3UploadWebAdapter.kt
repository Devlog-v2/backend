package com.project.devlog.infrastructure.adapter.presentation

import com.project.devlog.infrastructure.adapter.presentation.data.response.ImageUrlResponse
import com.project.devlog.infrastructure.application.usecase.S3UploadUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v2/image")
class S3UploadWebAdapter(
    private val s3UploadUseCase: S3UploadUseCase
) {

    @PostMapping
    fun uploadImage(@RequestPart image: MultipartFile): ResponseEntity<ImageUrlResponse> =
        s3UploadUseCase.execute(image)
            .let { ResponseEntity.ok(it) }

}