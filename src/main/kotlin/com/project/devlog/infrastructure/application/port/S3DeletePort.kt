package com.project.devlog.infrastructure.application.port

interface S3DeletePort {

    fun deleteFile(fileName: String)

}