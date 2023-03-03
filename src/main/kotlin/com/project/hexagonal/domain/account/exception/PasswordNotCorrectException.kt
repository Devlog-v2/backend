package com.project.hexagonal.domain.account.exception

import com.project.hexagonal.global.exception.HexagonalException
import com.project.hexagonal.global.exception.error.ErrorCode

class PasswordNotCorrectException: HexagonalException(ErrorCode.PASSWORD_NOT_CORRECT)