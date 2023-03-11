package com.project.hexagonal.domain.comment.application.port

import com.project.hexagonal.domain.comment.Comment

interface CommandCommentPort {

    fun saveComment(domain: Comment): Comment
    fun deleteComment(domain: Comment)

}