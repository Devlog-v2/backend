package com.project.devlog.domain.account.exception

import com.project.devlog.global.exception.DevlogV2Exception
import com.project.devlog.global.exception.error.ErrorCode

class DuplicateEmailException: DevlogV2Exception(ErrorCode.DUPLICATE_EMAIL)