package com.project.hexagonal.domain.account.application.port

interface PasswordEncodePort {

    fun encode(password: String): String
    fun match(encodedPassword: String, rawPassword: String): Boolean

}