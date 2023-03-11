package com.project.devlog.global.security.jwt.exception

import com.project.devlog.global.exception.DevlogV2Exception
import com.project.devlog.global.exception.error.ErrorCode

class ExpiredRefreshTokenExcpetion: DevlogV2Exception(ErrorCode.EXPIRED_REFRESH_TOKEN)