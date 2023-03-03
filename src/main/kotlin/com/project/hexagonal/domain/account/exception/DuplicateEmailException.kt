package com.project.hexagonal.domain.account.exception

import com.project.hexagonal.global.exception.HexagonalException
import com.project.hexagonal.global.exception.error.ErrorCode

class DuplicateEmailException: HexagonalException(ErrorCode.DUPLICATE_EMAIL)