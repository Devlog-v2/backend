package com.project.hexagonal.global.security

import com.project.hexagonal.global.filter.ExceptionHandlerFilter
import com.project.hexagonal.global.filter.JwtRequestFilter
import com.project.hexagonal.global.security.handler.CustomAccessDenideHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtRequestFilter: JwtRequestFilter,
    private val exceptionHandlerFilter: ExceptionHandlerFilter
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
            .antMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
            .antMatchers(HttpMethod.PATCH, "/api/v1/auth/**").permitAll()

            .antMatchers("/api/v1/admin/**").hasRole("ROLE_ADMIN")
            .antMatchers("/api/v1/user/**").hasRole("ROLE_USER")
            .anyRequest().authenticated()
            .and()

            .exceptionHandling()
            .accessDeniedHandler(CustomAccessDenideHandler())
            .and()

            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(exceptionHandlerFilter, JwtRequestFilter::class.java)
            .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

}