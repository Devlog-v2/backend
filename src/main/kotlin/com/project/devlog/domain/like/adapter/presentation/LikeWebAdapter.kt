package com.project.devlog.domain.like.adapter.presentation

import com.project.devlog.domain.like.application.usecase.DeleteLikeUseCase
import com.project.devlog.domain.like.application.usecase.SaveLikeUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("api/v2/like")
class LikeWebAdapter(
    private val saveLikeUseCase: SaveLikeUseCase,
    private val deleteLikeUseCase: DeleteLikeUseCase
) {

    @PostMapping("{postIdx}")
    fun saveLike(@PathVariable postIdx: UUID): ResponseEntity<Void> =
        saveLikeUseCase.execute(postIdx)
            .let { ResponseEntity.status(HttpStatus.CREATED).build() }

    @DeleteMapping("{postIdx}")
    fun deleteLike(@PathVariable postIdx: UUID): ResponseEntity<Void> =
        deleteLikeUseCase.execute(postIdx)
            .let { ResponseEntity.ok().build() }

}