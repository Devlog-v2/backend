package com.project.hexagonal.domain.account.adapter.persistence.repository

import com.project.hexagonal.domain.account.adapter.persistence.entity.RefreshTokenEntity
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository: CrudRepository<RefreshTokenEntity, String>