package com.project.devlog.domain.like.application.port

import com.project.devlog.domain.like.Like
import java.util.*

interface QueryLikePort {

    fun queryExistsLikeByAccountIdxAndPostIdx(accountIdx: UUID, postIdx: UUID): Boolean
    fun queryLikeCountByPostIdx(postIdx: UUID): Int
    fun queryLikeByAccountIdxAndPostIdx(accountIdx: UUID, postIdx: UUID): Like?

}