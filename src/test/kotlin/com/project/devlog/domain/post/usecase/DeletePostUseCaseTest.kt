package com.project.devlog.domain.post.usecase

import com.project.devlog.domain.post.Post
import com.project.devlog.domain.post.application.port.CommandPostPort
import com.project.devlog.domain.post.application.port.QueryPostPort
import com.project.devlog.domain.post.application.usecase.DeletePostUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDate
import java.util.*

class DeletePostUseCaseTest: BehaviorSpec({
    val commandPostPort = mockk<CommandPostPort>()
    val queryPostPort = mockk<QueryPostPort>()
    val deletePostUseCase = DeletePostUseCase(commandPostPort, queryPostPort)

    // account
    val accountIdx = UUID.randomUUID()

    // post
    val title = "test title"
    val content = "test content"
    val tag = mutableListOf("test tag1", "test tag2")
    val images = mutableListOf("test image1", "test image2")
    val createdAt = LocalDate.now()

    Given("postIdx가 주어졌을때") {
        val postIdx = UUID.randomUUID()
        val postDomain = Post(postIdx, title, content, accountIdx, tag, images, createdAt)

        every { queryPostPort.queryPostById(postIdx) } returns postDomain
        every { commandPostPort.deletePost(any()) } returns Unit

        When("게시글 생성을 요청하면") {
            deletePostUseCase.execute(postIdx)

            Then("게시글이 삭제 되어야 한다.") {
                verify(exactly = 1) { commandPostPort.deletePost(postDomain) }
            }
        }
    }
})