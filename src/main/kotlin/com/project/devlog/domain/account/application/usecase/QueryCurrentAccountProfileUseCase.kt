package com.project.devlog.domain.account.application.usecase

import com.project.devlog.domain.account.adapter.presentation.data.response.ProfileDetailResponse
import com.project.devlog.domain.account.application.port.AccountSecurityPort
import com.project.devlog.domain.account.application.port.QueryAccountPort
import com.project.devlog.domain.account.exception.AccountNotFoundException
import com.project.devlog.global.annotation.ReadOnlyUseCase

@ReadOnlyUseCase
class QueryCurrentAccountProfileUseCase(
    private val accountSecurityPort: AccountSecurityPort,
    private val queryAccountPort: QueryAccountPort
) {

    fun execute(): ProfileDetailResponse =
        queryAccountPort.queryAccountByIdx(accountSecurityPort.getCurrentAccountIdx()!!)
            .let { it ?: throw AccountNotFoundException() }
            .let {
                it.let {
                    ProfileDetailResponse(
                        accountIdx = it.idx,
                        email = it.email,
                        name = it.name,
                        profileUrl = it.profileUrl,
                        githubUrl = it.githubUrl,
                        service = it.service,
                        company = it.company,
                        readme = it.readme,
                        isMine = true
                    )
                }
            }

}