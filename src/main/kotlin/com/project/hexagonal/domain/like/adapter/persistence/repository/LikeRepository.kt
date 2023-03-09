package com.project.hexagonal.domain.like.adapter.persistence.repository

import com.project.hexagonal.domain.like.adapter.persistence.entity.LikeEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface LikeRepository: CrudRepository<LikeEntity, Long> {

    fun findByAccountIdxAndPostIdx(accountIdx: UUID, postIdx: UUID): LikeEntity
    fun countByPostIdx(postIdx: UUID): Int

}