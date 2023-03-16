package com.project.devlog.global.security.principal

import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class AccountDetails(
    private val accountIdx: UUID
): UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority(Authority.ROLE_ACCOUNT.name))

    override fun getPassword(): String? = null

    override fun getUsername(): String = accountIdx.toString()

    override fun isAccountNonExpired(): Boolean = false

    override fun isAccountNonLocked(): Boolean = false

    override fun isCredentialsNonExpired(): Boolean = false

    override fun isEnabled(): Boolean = false

}
