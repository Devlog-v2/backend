package com.project.devlog.domain.account.exception

import com.project.devlog.global.exception.DevlogV2Exception
import com.project.devlog.global.exception.error.ErrorCode

class AccountNotFoundException: DevlogV2Exception(ErrorCode.ACCOUNT_NOT_FOUND)