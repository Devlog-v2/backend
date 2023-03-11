package com.project.devlog.domain.like.adapter.persistence

import com.project.devlog.domain.like.Like
import com.project.devlog.domain.like.adapter.persistence.mapper.LikeConverter
import com.project.devlog.domain.like.adapter.persistence.repository.LikeRepository
import com.project.devlog.domain.like.application.port.LikePort
import com.project.devlog.global.annotation.AdapterWithTransaction
import java.util.*

@AdapterWithTransaction
class LikePersistenceAdapter(
    private val likeRepository: LikeRepository,
    private val likeConverter: LikeConverter
): LikePort {

    override fun saveLike(domain: Like): Like =
        likeConverter.toDomain(likeRepository.save(likeConverter.toEntity(domain)))

    override fun deleteLike(domain: Like) =
        likeRepository.delete(likeConverter.toEntity(domain))

    override fun queryExistsLikeByAccountIdxAndPostIdx(accountIdx: UUID, postIdx: UUID): Boolean =
        likeRepository.existsByAccountIdxAndPostIdx(accountIdx, postIdx)

    override fun queryLikeByAccountIdxAndPostIdx(accountIdx: UUID, postIdx: UUID): Like? =
        likeRepository.findByAccountIdxAndPostIdx(accountIdx, postIdx)?.let { likeConverter.toDomain(it) }

    override fun queryLikeCountByPostIdx(postIdx: UUID): Int =
        likeRepository.countByPostIdx(postIdx)

}