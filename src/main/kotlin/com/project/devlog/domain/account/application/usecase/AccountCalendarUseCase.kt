package com.project.devlog.domain.account.application.usecase

import com.project.devlog.domain.account.application.port.AccountSecurityPort
import com.project.devlog.domain.account.application.port.QueryAccountPort
import com.project.devlog.domain.account.exception.AccountNotFoundException
import com.project.devlog.domain.post.adapter.presentation.data.response.PostCalendarResponse
import com.project.devlog.domain.post.application.port.QueryPostPort
import com.project.devlog.global.annotation.ReadOnlyUseCase

@ReadOnlyUseCase
class AccountCalendarUseCase(
    private val accountSecurityPort: AccountSecurityPort,
    private val queryAccountPort: QueryAccountPort,
    private val queryPostPort: QueryPostPort
) {

    fun execute(): List<PostCalendarResponse> =
        queryAccountPort.queryAccountByIdx(accountSecurityPort.getCurrentAccountIdx()!!)
            .let { it ?: throw AccountNotFoundException() }
            .let { queryPostPort.queryCountByOneYearAgo(it.idx) }

}