package com.project.devlog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class DevlogV2Application

fun main(args: Array<String>) {
	runApplication<DevlogV2Application>(*args)
}
