package com.project.hexagonal.domain.like.adapter.persistence.repository

import com.project.hexagonal.domain.like.adapter.persistence.entity.LikeEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface LikeRepository: CrudRepository<LikeEntity, Long> {

    fun existsByAccountIdxAndPostIdx(accountIdx: UUID, postIdx: UUID): Boolean
    fun countByPostIdx(postIdx: UUID): Int

}