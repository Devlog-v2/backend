package com.project.devlog.domain.post.adapter.persistence

import com.project.devlog.domain.post.Post
import com.project.devlog.domain.post.adapter.persistence.converter.PostConverter
import com.project.devlog.domain.post.adapter.persistence.entity.toUpdate
import com.project.devlog.domain.post.adapter.persistence.repository.PostRepository
import com.project.devlog.domain.post.application.port.PostPort
import com.project.devlog.global.annotation.AdapterWithTransaction
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@AdapterWithTransaction
class PostPersistenceAdapter(
    private val postRepository: PostRepository,
    private val postconverter: PostConverter
): PostPort {

    override fun savePost(domain: Post): Post =
        postconverter.toDomain(postRepository.save(postconverter.toEntity(domain)))

    override fun updatePost(domain: Post) =
        postconverter.toEntity(domain)
            .let { it.toUpdate(it.title, it.content, it.tag, it.images) }

    override fun deletePost(domain: Post) =
        postRepository.delete(postconverter.toEntity(domain))

    override fun queryPostById(postIdx: UUID): Post? =
        postRepository.findByIdOrNull(postIdx)?.let { postconverter.toDomain(it) }

    override fun queryAllPost(pageRequest: PageRequest): List<Post> =
        postRepository.findAll().map { postconverter.toDomain(it) }

}