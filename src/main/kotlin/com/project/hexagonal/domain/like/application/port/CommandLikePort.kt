package com.project.hexagonal.domain.like.application.port

import com.project.hexagonal.domain.like.Like

interface CommandLikePort {

    fun saveLike(domain: Like): Like
    fun deleteLike(domain: Like)

}