package com.project.hexagonal.domain.post.adapter.persistence

import com.project.hexagonal.domain.post.Post
import com.project.hexagonal.domain.post.adapter.persistence.converter.PostConverter
import com.project.hexagonal.domain.post.adapter.persistence.entity.toUpdate
import com.project.hexagonal.domain.post.adapter.persistence.repository.PostRepository
import com.project.hexagonal.domain.post.application.port.PostPort
import com.project.hexagonal.global.annotation.AdapterWithTransaction
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import java.util.*

@AdapterWithTransaction
class PostPersistenceAdapter(
    private val postRepository: PostRepository,
    private val postconverter: PostConverter
) : PostPort {

    override fun savePost(post: Post): Post =
        postconverter.toDomain(postRepository.save(postconverter.toEntity(post)))

    override fun updatePost(post: Post) =
        postconverter.toEntity(post)
            .let { it.toUpdate(it.title, it.content, it.tag) }

    override fun deletePost(post: Post) =
        postRepository.delete(postconverter.toEntity(post))

    override fun queryPostById(postIdx: UUID): Post? =
        postRepository.findByIdOrNull(postIdx)?.let { postconverter.toDomain(it) }

    override fun queryAllPost(pageRequest: PageRequest): List<Post> =
        postRepository.findAll().map { postconverter.toDomain(it) }

}