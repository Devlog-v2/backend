package com.project.devlog.domain.account.application.usecase

import com.project.devlog.domain.account.adapter.presentation.data.request.UpdateProfileRequest
import com.project.devlog.domain.account.application.port.AccountSecurityPort
import com.project.devlog.domain.account.application.port.CommandAccountPort
import com.project.devlog.domain.account.application.port.QueryAccountPort
import com.project.devlog.domain.account.exception.AccountNotFoundException
import com.project.devlog.global.annotation.UseCase
import java.util.UUID

@UseCase
class UpdateProfileUseCase(
    private val commandAccountPort: CommandAccountPort,
    private val queryAccountPort: QueryAccountPort,
    private val accountSecurityPort: AccountSecurityPort
) {

    fun execute(reqeust: UpdateProfileRequest): UUID =
        queryAccountPort.queryAccountByIdx(accountSecurityPort.getCurrentAccountIdx()!!)
            .let { it ?: throw AccountNotFoundException() }
            .let {
                commandAccountPort.saveAccount(
                    it.copy(
                        name = reqeust.name,
                        profileUrl = reqeust.profileUrl,
                        company = reqeust.company,
                        githubUrl = reqeust.githubUrl,
                        readme = reqeust.readme
                    )
                ).idx
            }

}