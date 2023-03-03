package com.project.hexagonal.global.exception.handler

import com.project.hexagonal.global.exception.HexagonalException
import com.project.hexagonal.global.exception.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHanlder {

    @ExceptionHandler(HexagonalException::class)
    fun globalExceptionHandler(e: HexagonalException): ResponseEntity<ErrorResponse> =
        ResponseEntity(
            ErrorResponse(e.errorCode.message, e.errorCode.status),
            HttpStatus.valueOf(e.errorCode.status)
        )

}