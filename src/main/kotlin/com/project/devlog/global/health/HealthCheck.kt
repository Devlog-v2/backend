package com.project.devlog.global.health

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheck {

    @GetMapping("/")
    fun health(): ResponseEntity<String> =
        ResponseEntity.ok("GOOD")

}