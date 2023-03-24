package com.project.devlog.infrastructure.application.port

import org.springframework.web.multipart.MultipartFile

interface S3UploadPort {

    fun uploadImage(image: MultipartFile): String

}