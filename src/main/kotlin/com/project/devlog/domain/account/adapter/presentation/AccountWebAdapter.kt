package com.project.devlog.domain.account.adapter.presentation

import com.project.devlog.domain.account.adapter.presentation.data.request.UpdateProfileRequest
import com.project.devlog.domain.account.adapter.presentation.data.response.ProfileDetailResponse
import com.project.devlog.domain.account.application.usecase.*
import com.project.devlog.domain.post.adapter.presentation.data.response.PostCalendarResponse
import com.project.devlog.domain.post.adapter.presentation.data.response.PostListResponse
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("api/v2/account")
class AccountWebAdapter(
    private val queryCurrentAccountProfileUseCase: QueryCurrentAccountProfileUseCase,
    private val queryProfileDetailUseCase: QueryProfileDetailUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val queryAccountPostUseCase: QueryAccountPostUseCase,
    private val queryAccountPostSearchUseCase: QueryAccountPostSearchUseCase,
    private val accountCalendarUseCase: AccountCalendarUseCase,
    private val queryPostDateUseCase: QueryPostDateUseCase
) {

    @GetMapping
    fun findCurrentAccountPrifile(): ResponseEntity<ProfileDetailResponse> =
        queryCurrentAccountProfileUseCase.execute()
            .let { ResponseEntity.ok(it) }

    @GetMapping("{accountIdx}")
    fun findProfileByIdx(@PathVariable accountIdx: UUID): ResponseEntity<ProfileDetailResponse> =
        queryProfileDetailUseCase.execute(accountIdx)
            .let { ResponseEntity.ok(it) }

    @PatchMapping
    fun updateProfile(@RequestBody request: UpdateProfileRequest): ResponseEntity<Void> =
        updateProfileUseCase.execute(request)
            .let { ResponseEntity.ok().build() }

    @GetMapping("/post/{accountIdx}")
    fun findPostByAccountIdx(@PathVariable accountIdx: UUID): ResponseEntity<List<PostListResponse>> =
        queryAccountPostUseCase.execute(accountIdx)
            .let { ResponseEntity.ok(it) }

    @GetMapping("/search")
    fun searchAccountPost(
        @RequestParam accountIdx: UUID,
        @RequestParam title: String
    ): ResponseEntity<List<PostListResponse>> =
        queryAccountPostSearchUseCase.execute(accountIdx, title)
            .let { ResponseEntity.ok(it) }

    @GetMapping("/post")
    fun findPostByDate(
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate,
        @RequestParam accountIdx: UUID
    ): ResponseEntity<List<PostListResponse>> =
        queryPostDateUseCase.execute(date, accountIdx)
            .let { ResponseEntity.ok(it) }

    @GetMapping("calendar/{accountIdx}")
    fun calendarByPostCount(@PathVariable accountIdx: UUID): ResponseEntity<List<PostCalendarResponse>> =
        accountCalendarUseCase.execute(accountIdx)
            .let { ResponseEntity.ok(it) }

}