package com.project.devlog.global.exception

import com.project.devlog.global.exception.error.ErrorCode

open class DevlogV2Exception(val errorCode: ErrorCode): RuntimeException(errorCode.message)