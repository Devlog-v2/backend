package com.project.devlog.global.security.jwt.exception

import com.project.devlog.global.exception.DevlogV2Exception
import com.project.devlog.global.exception.error.ErrorCode

class InvalidTokenException: DevlogV2Exception(ErrorCode.INVALID_TOKEN)