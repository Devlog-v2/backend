package com.project.devlog.domain.comment.application.port

import com.project.devlog.domain.comment.Comment

interface CommandCommentPort {

    fun saveComment(domain: Comment): Comment
    fun updateComment(domain: Comment)
    fun deleteComment(domain: Comment)

}