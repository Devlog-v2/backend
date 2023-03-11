package com.project.devlog.global.security.jwt.exception

import com.project.devlog.global.exception.DevlogV2Exception
import com.project.devlog.global.exception.error.ErrorCode

class InvalidTokenTypeException: DevlogV2Exception(ErrorCode.INVALID_TOKEN_TPYE)