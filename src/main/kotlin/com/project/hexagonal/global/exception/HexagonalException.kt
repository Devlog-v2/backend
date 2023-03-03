package com.project.hexagonal.global.exception

import com.project.hexagonal.global.exception.error.ErrorCode

open class HexagonalException(val errorCode: ErrorCode): RuntimeException(errorCode.message)