package com.project.hexagonal.global.security.jwt.exception

import com.project.hexagonal.global.exception.HexagonalException
import com.project.hexagonal.global.exception.error.ErrorCode

class ExpiredRefreshTokenExcpetion: HexagonalException(ErrorCode.EXPIRED_REFRESH_TOKEN)