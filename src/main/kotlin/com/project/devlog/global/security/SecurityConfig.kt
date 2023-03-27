package com.project.devlog.global.security

import com.project.devlog.domain.account.adapter.presentation.data.enumType.Authority
import com.project.devlog.domain.account.application.port.JwtParserPort
import com.project.devlog.global.filter.config.FilterConfig
import com.project.devlog.global.security.handler.CustomAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtParserPort: JwtParserPort
) {

    @Bean
    protected fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .cors()
            .and()
            .csrf().disable()
            .httpBasic().disable()

            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()

            .authorizeRequests()

            // /auth
            .mvcMatchers(HttpMethod.POST, "/api/v2/auth/signup").permitAll()
            .mvcMatchers(HttpMethod.POST, "/api/v2/auth/signin").permitAll()
            .mvcMatchers(HttpMethod.PATCH, "/api/v2/auth/reissue").permitAll()

            // /account
            .mvcMatchers(HttpMethod.GET, "/api/v2/account").hasAnyAuthority(Authority.ROLE_ACCOUNT.name, Authority.ROLE_ADMIN.name)
            .mvcMatchers(HttpMethod.GET, "/api/v2/account/{accountIdx}").permitAll()
            .mvcMatchers(HttpMethod.PATCH, "/api/v2/account").hasAnyAuthority(Authority.ROLE_ACCOUNT.name, Authority.ROLE_ADMIN.name)
            .mvcMatchers(HttpMethod.GET, "/api/v2/account/post/{accountIdx}").permitAll()
            .mvcMatchers(HttpMethod.GET, "/api/v2/account/post/search/{accountIdx}").permitAll()
            .mvcMatchers(HttpMethod.GET, "/api/v2/account/post").permitAll()
            .mvcMatchers(HttpMethod.GET, "/api/v2/account/calendar").permitAll()

            // /post
            .mvcMatchers(HttpMethod.POST, "/api/v2/post").hasAnyAuthority(Authority.ROLE_ACCOUNT.name, Authority.ROLE_ADMIN.name)
            .mvcMatchers(HttpMethod.PATCH, "/api/v2/post/{postIdx}").hasAnyAuthority(Authority.ROLE_ACCOUNT.name, Authority.ROLE_ADMIN.name)
            .mvcMatchers(HttpMethod.DELETE, "/api/v2/post/{postIdx}").hasAnyAuthority(Authority.ROLE_ACCOUNT.name, Authority.ROLE_ADMIN.name)
            .mvcMatchers(HttpMethod.GET, "/api/v2/post").permitAll()
            .mvcMatchers(HttpMethod.GET, "/api/v2/post/{postIdx}").permitAll()
            .mvcMatchers(HttpMethod.GET, "/api/v2/post/search/**").permitAll()

            // /comment
            .mvcMatchers(HttpMethod.POST, "/api/v2/comment/{postIdx}").hasAnyAuthority(Authority.ROLE_ACCOUNT.name, Authority.ROLE_ADMIN.name)
            .mvcMatchers(HttpMethod.PATCH, "/api/v2/comment/{commentIdx}").hasAnyAuthority(Authority.ROLE_ACCOUNT.name, Authority.ROLE_ADMIN.name)
            .mvcMatchers(HttpMethod.DELETE, "/api/v2/comment/{commentIdx}").hasAnyAuthority(Authority.ROLE_ACCOUNT.name, Authority.ROLE_ADMIN.name)

            // /like
            .mvcMatchers(HttpMethod.POST, "/api/v2/like/{postIdx}").hasAnyAuthority(Authority.ROLE_ACCOUNT.name, Authority.ROLE_ADMIN.name)
            .mvcMatchers(HttpMethod.DELETE, "/api/v2/like/{postIdx}").hasAnyAuthority(Authority.ROLE_ACCOUNT.name, Authority.ROLE_ADMIN.name)

            // /image
            .mvcMatchers(HttpMethod.POST, "/api/v2/image").hasAnyAuthority(Authority.ROLE_ACCOUNT.name, Authority.ROLE_ADMIN.name)

            // /health
            .mvcMatchers(HttpMethod.GET, "/").permitAll()
            .anyRequest().denyAll()
            .and()

            .exceptionHandling()
            .authenticationEntryPoint(CustomAuthenticationEntryPoint())
            .and()

            .apply(FilterConfig(jwtParserPort))
            .and()
            .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

}