package com.project.hexagonal.domain.account.adapter.persistence.repository

import com.project.hexagonal.domain.account.adapter.persistence.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

// 영속성이랑 도메인 계층을 구분하기 위해서 ORM Entity를 따로 만들었들어 구분 하였다
interface AccountRepository: JpaRepository<AccountEntity, UUID>  {

    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): AccountEntity?
    fun findByIdx(idx: UUID): AccountEntity?

}

