package com.project.devlog.domain.account.adapter.presentation

import com.project.devlog.domain.account.application.usecase.AccountCalendarUseCase
import com.project.devlog.domain.post.adapter.presentation.data.response.PostCalendarResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v2/account")
class AccountWebAdapter(
    private val accountCalendarUseCase: AccountCalendarUseCase
) {

    @GetMapping("calendar")
    fun calendarByPostCount(): ResponseEntity<List<PostCalendarResponse>> =
        accountCalendarUseCase.execute()
            .let { ResponseEntity.ok(it) }

}