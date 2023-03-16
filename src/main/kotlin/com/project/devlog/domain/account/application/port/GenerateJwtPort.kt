package com.project.devlog.domain.account.application.port

import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import com.project.devlog.domain.account.adapter.presentation.data.response.SignInResponse
import java.util.UUID

interface GenerateJwtPort {

    fun generate(accountIdx: UUID, authority: Authority): SignInResponse

}