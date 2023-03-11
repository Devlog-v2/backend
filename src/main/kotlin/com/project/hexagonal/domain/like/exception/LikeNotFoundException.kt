package com.project.hexagonal.domain.like.exception

import com.project.hexagonal.global.exception.HexagonalException
import com.project.hexagonal.global.exception.error.ErrorCode

class LikeNotFoundException: HexagonalException(ErrorCode.LIKE_NOT_FOUND)