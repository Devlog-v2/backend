package com.project.hexagonal.domain.account.adapter.presentation.data.enumType

import org.springframework.security.core.GrantedAuthority

enum class Autority: GrantedAuthority {

    ROLE_ACCOUNT, ROLE_ADMIN;

    override fun getAuthority(): String = name

}