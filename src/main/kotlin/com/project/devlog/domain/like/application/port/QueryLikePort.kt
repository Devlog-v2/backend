package com.project.devlog.domain.like.application.port

import com.project.devlog.domain.like.Like
import java.util.*

interface QueryLikePort {

    fun queryExistsByAccountIdxAndPostIdx(accountIdx: UUID?, postIdx: UUID): Boolean
    fun queryByAccountIdxAndPostIdx(accountIdx: UUID, postIdx: UUID): Like?
    fun queryCountByPostIdx(postIdx: UUID): Int

}