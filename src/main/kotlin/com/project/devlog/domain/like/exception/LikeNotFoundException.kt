package com.project.devlog.domain.like.exception

import com.project.devlog.global.exception.DevlogV2Exception
import com.project.devlog.global.exception.error.ErrorCode

class LikeNotFoundException: DevlogV2Exception(ErrorCode.LIKE_NOT_FOUND)