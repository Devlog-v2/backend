package com.project.devlog.domain.like.application.port

import com.project.devlog.domain.like.Like

interface CommandLikePort {

    fun saveLike(domain: Like): Like
    fun deleteLike(domain: Like)

}