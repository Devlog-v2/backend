package com.project.hexagonal.domain.account.application.port

import com.project.hexagonal.domain.account.adapter.presentation.data.response.SignInResponse

interface GenetateJwtPort {

    fun generate(email: String): SignInResponse

}