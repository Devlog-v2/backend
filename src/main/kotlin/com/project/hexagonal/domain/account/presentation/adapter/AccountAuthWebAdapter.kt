package com.project.hexagonal.domain.account.presentation.adapter

import com.project.hexagonal.domain.account.application.usecase.SignUpUseCase
import com.project.hexagonal.domain.account.presentation.data.request.SignUpRequest
import com.project.hexagonal.domain.account.presentation.data.request.toDomain
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AccountAuthWebAdapter(
    private val signUpUseCase: SignUpUseCase
) {

    @PostMapping("signup")
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<Void> =
        signUpUseCase.execute(request.toDomain())
            .let { ResponseEntity.status(HttpStatus.CREATED).build() }

}