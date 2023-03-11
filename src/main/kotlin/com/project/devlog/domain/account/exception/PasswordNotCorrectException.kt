package com.project.devlog.domain.account.exception

import com.project.devlog.global.exception.DevlogV2Exception
import com.project.devlog.global.exception.error.ErrorCode

class PasswordNotCorrectException: DevlogV2Exception(ErrorCode.PASSWORD_NOT_CORRECT)