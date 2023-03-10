package com.project.devlog.global.security.principal

import com.project.devlog.domain.account.adapter.persistence.entity.AccountEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AccountDetails(
    private val account: AccountEntity
): UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = account.authority

    override fun getPassword(): String? = null

    override fun getUsername(): String = account.idx.toString()

    override fun isAccountNonExpired(): Boolean = false

    override fun isAccountNonLocked(): Boolean = false

    override fun isCredentialsNonExpired(): Boolean = false

    override fun isEnabled(): Boolean = false

}
