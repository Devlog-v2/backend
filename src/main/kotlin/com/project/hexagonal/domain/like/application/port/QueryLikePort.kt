package com.project.hexagonal.domain.like.application.port

import com.project.hexagonal.domain.like.Like
import java.util.*

interface QueryLikePort {

    fun queryExstistLikeByAccountIdxAndPostIdx(accountIdx: UUID, postIdx: UUID): Boolean
    fun queryLikeCountByPostIdx(postIdx: UUID): Int
    fun queryLikeByAccountIdxAndPostIdx(accountIdx: UUID, postIdx: UUID): Like?

}