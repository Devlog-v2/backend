package com.project.hexagonal.domain.account.adapter.password

import com.project.hexagonal.domain.account.application.port.PasswordEncodePort
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncoderAdapter(
    private val passwordEncoder: PasswordEncoder
): PasswordEncodePort {

    override fun encode(password: String): String = passwordEncoder.encode(password)

    override fun match(rawPassword: String, encodedPassword: String): Boolean = passwordEncoder.matches(rawPassword, encodedPassword)

}