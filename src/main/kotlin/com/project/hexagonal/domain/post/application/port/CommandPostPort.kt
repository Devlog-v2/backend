package com.project.hexagonal.domain.post.application.port

import com.project.hexagonal.domain.post.Post

interface CommandPostPort {

    fun savePost(post: Post): Post
    fun updatePost(post: Post)
    fun deletePost(post: Post)

}