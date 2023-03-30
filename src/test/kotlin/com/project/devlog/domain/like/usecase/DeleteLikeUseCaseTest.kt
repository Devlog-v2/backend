package com.project.devlog.domain.like.usecase

import com.project.devlog.domain.account.application.port.AccountSecurityPort
import com.project.devlog.domain.like.Like
import com.project.devlog.domain.like.application.port.CommandLikePort
import com.project.devlog.domain.like.application.port.QueryLikePort
import com.project.devlog.domain.like.application.usecase.DeleteLikeUseCase
import com.project.devlog.domain.like.exception.LikeNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class DeleteLikeUseCaseTest: BehaviorSpec({
    val commandLikePort = mockk<CommandLikePort>()
    val queryLikePort = mockk<QueryLikePort>()
    val accountSecurityPort = mockk<AccountSecurityPort>()
    val deleteLikeUseCase = DeleteLikeUseCase(commandLikePort, queryLikePort, accountSecurityPort)

    // like
    val likeIdx = 1L
    val isLiked = false

    Given("account와 postIdx가 주어졌을때") {
        val accountIdx = UUID.randomUUID()
        val postIdx = UUID.randomUUID()

        val likeDomain = Like(likeIdx, isLiked, accountIdx, postIdx)

        every { accountSecurityPort.getCurrentAccountIdx() } returns accountIdx
        every { queryLikePort.queryByAccountIdxAndPostIdx(accountIdx, postIdx) } returns likeDomain
        every { commandLikePort.deleteLike(any()) } returns Unit

        When("게시글 좋아요 취소 요청을 하면") {
            deleteLikeUseCase.execute(postIdx)

            Then("좋아요 수가 줄어들어야한다.") {
                verify(exactly = 1) { commandLikePort.deleteLike(any()) }
            }
        }

        When("좋아요를 누르지 않은 계정이라면") {
            every { queryLikePort.queryByAccountIdxAndPostIdx(accountIdx, postIdx) } returns null

            Then("LikeNotFoundException 터져야 한다.") {
                shouldThrow<LikeNotFoundException> {
                    deleteLikeUseCase.execute(postIdx)
                }
            }
        }

    }

})