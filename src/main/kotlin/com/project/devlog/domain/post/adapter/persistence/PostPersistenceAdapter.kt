package com.project.devlog.domain.post.adapter.persistence

import com.project.devlog.domain.post.Post
import com.project.devlog.domain.post.adapter.persistence.converter.PostConverter
import com.project.devlog.domain.post.adapter.persistence.repository.PostRepository
import com.project.devlog.domain.post.adapter.persistence.repository.PostRepositoryCustom
import com.project.devlog.domain.post.adapter.presentation.data.response.PostCalendarResponse
import com.project.devlog.domain.post.adapter.presentation.data.response.PostListResponse
import com.project.devlog.domain.post.application.port.PostPort
import com.project.devlog.global.annotation.AdapterWithTransaction
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@AdapterWithTransaction
class PostPersistenceAdapter(
    private val postRepository: PostRepository,
    private val postRepositoryCustom: PostRepositoryCustom,
    private val postconverter: PostConverter
): PostPort {

    override fun savePost(domain: Post): Post =
        postconverter.toDomain(postRepository.save(postconverter.toEntity(domain)))

    override fun deletePost(domain: Post) =
        postRepository.delete(postconverter.toEntity(domain))

    override fun queryPostById(postIdx: UUID): Post? =
        postRepository.findByIdOrNull(postIdx)?.let { postconverter.toDomain(it) }

    override fun queryAllPost(): List<Post> =
        postRepository.findAllByOrderByCreatedAtDesc().map { postconverter.toDomain(it) }

    override fun queryCountByOneYearAgo(accountIdx: UUID): List<PostCalendarResponse> =
        postRepositoryCustom.countByOneYearAgo(accountIdx)

    override fun querySearchByTitle(title: String): List<Post> =
        postRepository.findByTitleContaining(title).map { postconverter.toDomain(it) }

}