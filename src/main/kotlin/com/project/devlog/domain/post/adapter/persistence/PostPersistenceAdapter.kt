package com.project.devlog.domain.post.adapter.persistence

import com.project.devlog.domain.post.Post
import com.project.devlog.domain.post.adapter.persistence.converter.PostConverter
import com.project.devlog.domain.post.adapter.persistence.repository.PostRepository
import com.project.devlog.domain.post.adapter.persistence.repository.PostRepositoryCustom
import com.project.devlog.domain.post.adapter.presentation.data.response.PostCalendarResponse
import com.project.devlog.domain.post.application.port.PostPort
import com.project.devlog.global.annotation.AdapterWithTransaction
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate
import java.util.*

@AdapterWithTransaction
class PostPersistenceAdapter(
    private val postRepository: PostRepository,
    private val postRepositoryCustom: PostRepositoryCustom,
    private val postConverter: PostConverter
): PostPort {

    override fun savePost(domain: Post): Post =
        postConverter.toDomain(postRepository.save(postConverter.toEntity(domain)))

    override fun deletePost(domain: Post) =
        postRepository.delete(postConverter.toEntity(domain))

    override fun queryPostById(postIdx: UUID): Post? =
        postRepository.findByIdOrNull(postIdx)?.let { postConverter.toDomain(it) }

    override fun queryAllPost(): List<Post> =
        postRepository.findAllByOrderByCreatedAtDesc().map { postConverter.toDomain(it) }

    override fun queryCountByOneYearAgo(accountIdx: UUID): List<PostCalendarResponse> =
        postRepositoryCustom.countByOneYearAgo(accountIdx)

    override fun querySearchByTitle(title: String): List<Post> =
        postRepository.findByTitleContainingOrderByCreatedAtDesc(title).map { postConverter.toDomain(it) }

    override fun queryAllPostByAccountIdx(accountIdx: UUID): List<Post> =
        postRepository.findByAccountIdxOrderByCreatedAtDesc(accountIdx).map { postConverter.toDomain(it) }

    override fun querySearchPostByAccountIdx(accountIdx: UUID, title: String): List<Post> =
        postRepository.findByAccountIdxAndTitleContainingOrderByCreatedAtDesc(accountIdx, title).map { postConverter.toDomain(it) }

    override fun queryPostByDateAndAccountIdx(date: LocalDate, accountIdx: UUID): List<Post> =
        postRepository.findByCreatedAtAndAccountIdx(date, accountIdx).map { postConverter.toDomain(it) }

}