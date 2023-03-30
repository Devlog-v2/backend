package com.project.devlog.domain.account.application.usecase

import com.project.devlog.domain.account.adapter.presentation.data.response.ProfileDetailResponse
import com.project.devlog.domain.account.application.port.AccountSecurityPort
import com.project.devlog.domain.account.application.port.QueryAccountPort
import com.project.devlog.domain.account.exception.AccountNotFoundException
import com.project.devlog.global.annotation.ReadOnlyUseCase
import java.util.UUID

@ReadOnlyUseCase
class QueryProfileDetailUseCase(
    private val queryAccountPort: QueryAccountPort,
    private val accountSecurityPort: AccountSecurityPort
) {

    fun execute(accountIdx: UUID): ProfileDetailResponse =
        queryAccountPort.queryAccountByIdx(accountIdx)
            .let { it ?: throw AccountNotFoundException() }
            .let {
                ProfileDetailResponse(
                    accountIdx = it.idx,
                    email = it.email,
                    name = it.name,
                    profileUrl = it.profileUrl,
                    githubUrl = it.githubUrl,
                    service = it.service,
                    company = it.company,
                    readme = it.readme,
                    isMine = it.idx == accountSecurityPort.getCurrentAccountIdx()
                )
            }

}