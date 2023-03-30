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

    fun execute(request: UpdateProfileRequest): UUID =
        queryAccountPort.queryAccountByIdx(accountSecurityPort.getCurrentAccountIdx()!!)
            .let { it ?: throw AccountNotFoundException() }
            .let {
                commandAccountPort.saveAccount(
                    it.copy(
                        name = request.name,
                        profileUrl = request.profileUrl,
                        githubUrl = request.githubUrl,
                        service = request.service,
                        company = request.company,
                        readme = request.readme
                    )
                ).idx
            }

}