package com.project.hexagonal.domain.post.exception

import com.project.hexagonal.global.exception.HexagonalException
import com.project.hexagonal.global.exception.error.ErrorCode

class PostNotFoundException: HexagonalException(ErrorCode.POST_NOT_FOUND)