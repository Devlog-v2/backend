package com.project.hexagonal.global.filter

import com.project.hexagonal.domain.account.application.port.JwtParserPort
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtRequestFilter(
    private val jwtParserPort: JwtParserPort // filter가 과연 port를 의존해도 되는걸까..?
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val accessToken = jwtParserPort.parseAccessToken(request)
        if(!accessToken.isNullOrBlank()) {
            val authentication = jwtParserPort.authentication(accessToken)
            SecurityContextHolder.clearContext() // securityContext 삭제
            val securityContext = SecurityContextHolder.getContext()
            securityContext.authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

}