package com.project.devlog.infrastructure.s3.application.port

interface S3DeletePort {

    fun deleteFile(fileName: String)

}