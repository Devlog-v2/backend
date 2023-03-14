package com.project.devlog.infrastructure.s3.application.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "aws")
class S3BocketProperties(
    val bucket: String
)