package com.project.devlog.domain.comment.exception

import com.project.devlog.global.exception.DevlogV2Exception
import com.project.devlog.global.exception.error.ErrorCode

class CommentNotFountException: DevlogV2Exception(ErrorCode.COMMENT_NOT_FOUND)