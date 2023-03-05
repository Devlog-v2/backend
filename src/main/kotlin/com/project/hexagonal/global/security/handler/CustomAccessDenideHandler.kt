package com.project.hexagonal.global.security.handler

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAccessDenideHandler: AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN) // 인증이 없는 경우 401, 권한이 없는 경우 403
    }

}