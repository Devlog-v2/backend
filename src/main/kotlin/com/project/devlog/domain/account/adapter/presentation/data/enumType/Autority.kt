package com.project.devlog.domain.account.adapter.presentation.data.enumType

import org.springframework.security.core.GrantedAuthority

enum class Autority: GrantedAuthority {

    ROLE_ACCOUNT, ROLE_ADMIN;

    override fun getAuthority(): String = name

}