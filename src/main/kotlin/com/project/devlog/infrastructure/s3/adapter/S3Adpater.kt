package com.project.devlog.infrastructure.s3.adapter

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.project.devlog.global.annotation.Adapter
import com.project.devlog.infrastructure.s3.application.port.S3Port
import com.project.devlog.infrastructure.s3.application.property.S3BocketProperties
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Adapter
class S3Adpater(
    private val amazonS3: AmazonS3,
    private val s3BocketProperties: S3BocketProperties
): S3Port {

    override fun uploadFile(fileList: List<MultipartFile>, dirName: String): List<String> {

        val fileNameList = mutableListOf<String>()

        fileList.map { file ->
            val fileName = fileNameToUUID(file.originalFilename.toString())
            val objectMetadata = ObjectMetadata()
            objectMetadata.contentLength = file.size
            objectMetadata.contentType = file.contentType

            runCatching {
                amazonS3.putObject(
                    PutObjectRequest(s3BocketProperties.bucket, dirName + fileName, file.inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
                )
            }.onSuccess {
                fileNameList.add(fileName)
            }.onFailure {
                throw IllegalArgumentException("파일 업로드에 실패하였습니다.")
            }
        }

        return fileNameList

    }

    override fun deleteFile(fileName: String) =
        amazonS3.deleteObject(DeleteObjectRequest(s3BocketProperties.bucket, fileName))

    private fun fileNameToUUID(fileName: String): String =
        UUID.randomUUID().toString().plus(getFileExtension(fileName))

    private fun getFileExtension(fileName: String): String =
        try {
            fileName.substring(fileName.lastIndexOf("."))
        } catch (e: StringIndexOutOfBoundsException) {
            throw IllegalArgumentException()
        }

}