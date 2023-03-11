package com.project.hexagonal.global.exception.error

enum class ErrorCode(
    val message: String,
    val status: Int
) {

    // ACCOUNT
    DUPLICATE_EMAIL("중복된 이메일 입니다.", 409),
    ACCOUNT_NOT_FOUND("계정을 찾을 수 없습니다.", 404),
    PASSWORD_NOT_CORRECT("비밀번호가 일치하지 않습니다.", 400),

    // JWT
    INVALID_TOKEN_TPYE("유효하지 않은 토큰 타입 입니다.", 403),
    INVALID_TOKEN("유효하지 않은 토큰 입니다.", 403),
    EXPIRED_REFRESH_TOKEN("만료된 refreshToken 입니다.", 403),
    EXPIRED_ACCESS_TOKEN("만료된 accessToken 입니다.", 403),

    // POST
    POST_NOT_FOUND("게시글을 찾을 수 없습니다.", 404),

    // LIKE
    LIKE_NOT_FOUND("좋아요를 누르지 않은 계정입니다.", 404),

    // SERVER
    INTERVAL_SERVER_ERROR("서버 오류 입니다.", 500),

}