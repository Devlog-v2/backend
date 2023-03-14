package com.project.devlog.infrastructure.s3.application.port

import org.springframework.web.multipart.MultipartFile

interface S3UploadPort {

    fun uploadFile(fileList: List<MultipartFile>, dirName: String): List<String>

}