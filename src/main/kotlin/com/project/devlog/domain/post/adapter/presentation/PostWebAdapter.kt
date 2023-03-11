package com.project.devlog.domain.post.adapter.presentation

import com.project.devlog.domain.post.adapter.presentation.data.request.UpdatePostRequest
import com.project.devlog.domain.post.adapter.presentation.data.request.WritePostRequest
import com.project.devlog.domain.post.adapter.presentation.data.response.PostDetailResponse
import com.project.devlog.domain.post.adapter.presentation.data.response.PostListResponse
import com.project.devlog.domain.post.application.usecase.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("post")
class PostWebAdapter(
    private val savePostUseCase: SavePostUseCase,
    private val updatePostUseCase: UpdatePostUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val queryPostDetatilUseCase: QueryPostDetatilUseCase,
    private val queryPostAllPostUseCase: QueryAllPostUseCase,
) {

    @PostMapping
    fun savePost(@RequestBody request: WritePostRequest): ResponseEntity<Void> =
        savePostUseCase.execute(request)
            .let { ResponseEntity.status(HttpStatus.CREATED).build() }

    @PatchMapping("{postIdx}")
    fun updatePost(@PathVariable postIdx: UUID, @RequestBody request: UpdatePostRequest): ResponseEntity<Void> =
        updatePostUseCase.execute(postIdx, request)
            .let { ResponseEntity.ok().build() }

    @DeleteMapping("{postIdx}")
    fun deletePost(@PathVariable postIdx: UUID): ResponseEntity<Void> =
        deletePostUseCase.execute(postIdx)
            .let { ResponseEntity.ok().build() }

    @GetMapping("{postIdx}")
    fun findPostDetatil(@PathVariable postIdx: UUID): ResponseEntity<PostDetailResponse> =
        queryPostDetatilUseCase.execute(postIdx)
            .let { ResponseEntity.ok(it) }

    @PostMapping
    fun findAllPost(@RequestParam page: Int, size: Int): ResponseEntity<List<PostListResponse>> =
        queryPostAllPostUseCase.execute(page, size)
            .let { ResponseEntity.ok(it) }

}