package com.project.devlog.domain.post.application.port

import com.project.devlog.domain.post.Post

interface CommandPostPort {

    fun savePost(domain: Post): Post
    fun deletePost(domain: Post)

}