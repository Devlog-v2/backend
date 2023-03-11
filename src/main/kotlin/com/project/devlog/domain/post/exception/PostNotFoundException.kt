package com.project.devlog.domain.post.exception

import com.project.devlog.global.exception.DevlogV2Exception
import com.project.devlog.global.exception.error.ErrorCode

class PostNotFoundException: DevlogV2Exception(ErrorCode.POST_NOT_FOUND)