package com.project.hexagonal.domain.account.adapter.presentation

import com.project.hexagonal.domain.account.adapter.presentation.data.request.SignInRequest
import com.project.hexagonal.domain.account.application.usecase.SignUpUseCase
import com.project.hexagonal.domain.account.adapter.presentation.data.request.SignUpRequest
import com.project.hexagonal.domain.account.adapter.presentation.data.response.SignInResponse
import com.project.hexagonal.domain.account.application.usecase.ReissueTokenUseCase
import com.project.hexagonal.domain.account.application.usecase.SignInUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AccountAuthWebAdapter(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
    private val reissueTokenUseCase: ReissueTokenUseCase
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<Void> =
        signUpUseCase.execute(request)
            .let { ResponseEntity.status(HttpStatus.CREATED).build() }

    @PostMapping("/signin")
    fun signIn(@RequestBody request: SignInRequest): ResponseEntity<SignInResponse> =
        signInUseCase.execute(request)
            .let { ResponseEntity.ok(it) }

    @PatchMapping("/reissue")
    fun reissueToken(@RequestHeader refreshToken: String): ResponseEntity<SignInResponse> =
        reissueTokenUseCase.execute(refreshToken)
            .let { ResponseEntity.ok(it) }

}