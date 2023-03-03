package com.project.hexagonal.domain.account.exception

import com.project.hexagonal.global.exception.HexagonalException
import com.project.hexagonal.global.exception.error.ErrorCode

class AccountNotFoundException: HexagonalException(ErrorCode.ACCOUNT_NOT_FOUND)