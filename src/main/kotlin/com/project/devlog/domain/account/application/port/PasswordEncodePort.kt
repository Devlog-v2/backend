package com.project.devlog.domain.account.application.port

interface PasswordEncodePort {

    fun passwordEncode(password: String): String
    fun isPasswordMatch(rawPassword: String, encodedPassword: String): Boolean

}