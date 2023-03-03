package com.project.hexagonal.domain.account.adapter.web.data.enumType

import org.springframework.security.core.GrantedAuthority

enum class Autority: GrantedAuthority {

    ROLE_USER, ROLE_ADMIN;

    override fun getAuthority(): String = name

}