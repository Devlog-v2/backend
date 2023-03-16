package com.project.devlog.domain.post.adapter.presentation

import com.project.devlog.domain.post.adapter.presentation.data.request.UpdatePostRequest
import com.project.devlog.domain.post.adapter.presentation.data.request.WritePostRequest
import com.project.devlog.domain.post.adapter.presentation.data.response.PostDetailResponse
import com.project.devlog.domain.post.adapter.presentation.data.response.PostListResponse
import com.project.devlog.domain.post.application.usecase.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("api/v2/post")
class PostWebAdapter(
    private val savePostUseCase: SavePostUseCase,
    private val updatePostUseCase: UpdatePostUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val queryPostDetatilUseCase: QueryPostDetatilUseCase,
    private val queryPostAllPostUseCase: QueryAllPostUseCase,
    private val queryPostSearchUseCase: QueryPostSearchUseCase
) {

    @PostMapping
    fun savePost(
        @RequestPart(value = "file") fileList: MutableList<MultipartFile>?,
        @RequestPart request: WritePostRequest
    ): ResponseEntity<Void> =
        savePostUseCase.execute(fileList, request)
            .let { ResponseEntity.status(HttpStatus.CREATED).build() }

    @PatchMapping("{postIdx}")
    fun updatePost(
        @PathVariable postIdx: UUID,
        @RequestPart(value = "file") fileList: MutableList<MultipartFile>?,
        @RequestPart request: UpdatePostRequest
    ): ResponseEntity<Void> =
        updatePostUseCase.execute(postIdx, fileList, request)
            .let { ResponseEntity.ok().build() }

    @DeleteMapping("{postIdx}")
    fun deletePost(@PathVariable postIdx: UUID): ResponseEntity<Void> =
        deletePostUseCase.execute(postIdx)
            .let { ResponseEntity.ok().build() }

    @GetMapping("{postIdx}")
    fun findPostDetatil(@PathVariable postIdx: UUID): ResponseEntity<PostDetailResponse> =
        queryPostDetatilUseCase.execute(postIdx)
            .let { ResponseEntity.ok(it) }

    @GetMapping
    fun findAllPost(): ResponseEntity<List<PostListResponse>> =
        queryPostAllPostUseCase.execute()
            .let { ResponseEntity.ok(it) }

    @GetMapping("search")
    fun findAllPost(@RequestParam title: String): ResponseEntity<List<PostListResponse>> =
        queryPostSearchUseCase.execute(title)
            .let { ResponseEntity.ok(it) }

}