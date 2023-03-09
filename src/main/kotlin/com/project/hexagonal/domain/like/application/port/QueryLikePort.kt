package com.project.hexagonal.domain.like.application.port

import java.util.*

interface QueryLikePort {

    fun queryLikeByAccountIdxAndPostIdx(accountIdx: UUID, postIdx: UUID): Boolean
    fun queryLikeCountByPostIdx(postIdx: UUID): Int

}