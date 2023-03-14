package com.project.devlog.domain.comment.application.port

import com.project.devlog.domain.comment.Comment
import java.util.UUID

interface QueryCommentPort {

    fun queryByCommentIdx(commentIdx: UUID): Comment?
    fun queryExsistByCommentIdxAndAccountIdx(commentIdx: UUID, postIdx: UUID): Boolean
    fun queryByPostIdx(postIdx: UUID): List<Comment>


}