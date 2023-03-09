package com.project.hexagonal.domain.like.application.port

import com.project.hexagonal.domain.like.Like
import java.util.UUID

interface QueryLikePort {

    fun queryLikeByAccountIdxAndPostIdx(accountIdx: UUID, postIdx: UUID): Like
    fun queryLikeCountByPostIdx(postIdx: UUID): Int

}