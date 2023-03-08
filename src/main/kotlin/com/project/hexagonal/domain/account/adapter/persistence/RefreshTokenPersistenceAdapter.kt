package com.project.hexagonal.domain.account.adapter.persistence

import com.project.hexagonal.domain.account.RefreshToken
import com.project.hexagonal.domain.account.adapter.persistence.entity.toDomain
import com.project.hexagonal.domain.account.adapter.persistence.repository.RefreshTokenRepository
import com.project.hexagonal.domain.account.application.port.RefreshTokenPort
import com.project.hexagonal.global.annotation.AdapterWithTransaction
import com.project.hexagonal.global.security.jwt.exception.InvalidTokenException
import org.springframework.data.repository.findByIdOrNull

@AdapterWithTransaction
class RefreshTokenPersistenceAdapter(
    private val refreshTokenRepository: RefreshTokenRepository
): RefreshTokenPort {

    override fun queryByRefreshToken(refreshToken: String): RefreshToken =
        refreshTokenRepository.findByIdOrNull(refreshToken)
            .let { it ?: throw InvalidTokenException() }.toDomain()

}