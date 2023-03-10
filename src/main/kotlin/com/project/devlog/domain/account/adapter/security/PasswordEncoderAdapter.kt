package com.project.devlog.domain.account.adapter.security

import com.project.devlog.domain.account.application.port.PasswordEncodePort
import com.project.devlog.global.annotation.Adapter
import org.springframework.security.crypto.password.PasswordEncoder

@Adapter
class PasswordEncoderAdapter(
    private val passwordEncoder: PasswordEncoder
): PasswordEncodePort {

    override fun passwordEncode(password: String): String = passwordEncoder.encode(password)

    override fun isPasswordMatch(rawPassword: String, encodedPassword: String): Boolean = passwordEncoder.matches(rawPassword, encodedPassword)

}