package com.project.hexagonal.domain.account.application.port

interface PasswordEncodePort {

    fun encode(password: String): String
    fun match(rawPassword: String, encodedPassword: String): Boolean

}