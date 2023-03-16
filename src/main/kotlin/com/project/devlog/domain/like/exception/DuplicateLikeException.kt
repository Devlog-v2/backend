package com.project.devlog.domain.like.exception

import com.project.devlog.global.exception.DevlogV2Exception
import com.project.devlog.global.exception.error.ErrorCode

class DuplicateLikeException: DevlogV2Exception(ErrorCode.DUPLICATE_LIKE)