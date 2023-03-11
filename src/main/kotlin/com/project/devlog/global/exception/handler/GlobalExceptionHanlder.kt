package com.project.devlog.global.exception.handler

import com.project.devlog.global.exception.DevlogV2Exception
import com.project.devlog.global.exception.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHanlder {

    @ExceptionHandler(DevlogV2Exception::class)
    fun globalExceptionHandler(e: DevlogV2Exception): ResponseEntity<ErrorResponse> =
        ResponseEntity(
            ErrorResponse(e.errorCode.message, e.errorCode.status),
            HttpStatus.valueOf(e.errorCode.status)
        )

}