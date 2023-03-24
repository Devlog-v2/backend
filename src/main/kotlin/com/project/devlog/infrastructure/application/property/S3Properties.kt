package com.project.devlog.infrastructure.application.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "aws")
class S3Properties(
    val bucket: String,
    val url: String
)