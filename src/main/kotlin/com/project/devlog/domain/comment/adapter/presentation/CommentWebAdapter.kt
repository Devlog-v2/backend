package com.project.devlog.domain.comment.adapter.presentation

import com.project.devlog.domain.comment.adapter.presentation.data.request.UpdateCommentRequest
import com.project.devlog.domain.comment.adapter.presentation.data.request.WriteCommentRequest
import com.project.devlog.domain.comment.application.usecase.DeleteCommentUseCase
import com.project.devlog.domain.comment.application.usecase.SaveCommentUseCase
import com.project.devlog.domain.comment.application.usecase.UpdateCommentUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("api/v2/comment")
class CommentWebAdapter(
    private val saveCommentUseCase: SaveCommentUseCase,
    private val updateCommentUseCase: UpdateCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase
) {

    @PostMapping("{postIdx}")
    fun saveLike(@PathVariable postIdx: UUID, @RequestBody request: WriteCommentRequest): ResponseEntity<Void> =
        saveCommentUseCase.execute(postIdx, request)
            .let { ResponseEntity.status(HttpStatus.CREATED).build() }

    @PatchMapping("{commentIdx}")
    fun updateComment(@PathVariable commentIdx: UUID, @RequestBody request: UpdateCommentRequest): ResponseEntity<Void> =
        updateCommentUseCase.execute(commentIdx, request)
            .let { ResponseEntity.ok().build() }

    @DeleteMapping("{commentIdx}")
    fun deleteComment(@PathVariable commentIdx: UUID): ResponseEntity<Void> =
        deleteCommentUseCase.execute(commentIdx)
            .let { ResponseEntity.ok().build() }

}