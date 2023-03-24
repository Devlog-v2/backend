package com.project.devlog.infrastructure.adapter

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.project.devlog.global.annotation.Adapter
import com.project.devlog.infrastructure.application.port.S3Port
import com.project.devlog.infrastructure.application.property.S3Properties
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Adapter
class S3Adpater(
    private val amazonS3: AmazonS3,
    private val s3Properties: S3Properties
): S3Port {

    override fun uploadImage(image: MultipartFile): String {
        val imageUrl = fileNameToUUID(image.originalFilename.toString())
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentLength = image.size
        objectMetadata.contentType = image.contentType

        runCatching {
            amazonS3.putObject(
                PutObjectRequest(s3Properties.bucket, imageUrl, image.inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead)
            )
        }.onFailure {
                throw IllegalArgumentException("파일 업로드에 실패하였습니다.")
        }

        return s3Properties.url + imageUrl
    }

    override fun deleteFile(fileName: String) =
        amazonS3.deleteObject(DeleteObjectRequest(s3Properties.bucket, fileName))

    private fun fileNameToUUID(fileName: String): String =
        UUID.randomUUID().toString().plus(getFileExtension(fileName))

    private fun getFileExtension(fileName: String): String =
        try {
            fileName.substring(fileName.lastIndexOf("."))
        } catch (e: StringIndexOutOfBoundsException) {
            throw IllegalArgumentException()
        }

}