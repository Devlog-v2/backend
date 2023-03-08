package com.project.hexagonal.domain.account.application.port

import com.project.hexagonal.domain.account.adapter.presentation.data.response.SignInResponse
import java.util.UUID

interface GenetateJwtPort {

    fun generate(accountIdx: UUID): SignInResponse

}