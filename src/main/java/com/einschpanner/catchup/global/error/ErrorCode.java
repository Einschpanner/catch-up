package com.einschpanner.catchup.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", "Method Not Allowed"),
    HANDLE_ACCESS_DENIED(403, "C003", "Access is Denied"),
    INTERNAL_SERVER_ERROR(500, "C004", "Interval Server Error"),
    ENTITY_NOT_FOUND(400, "C005", "Entity Not Found"),

    // USER
    USER_NOT_FOUND(404, "U001", "사용자를 찾을 수 없습니다."),

    // POST
    POST_NOT_FOUND(404, "P001", "게시글을 찾을 수 없습니다."),
    POST_ACCESS_DENIED(403, "P002", "게시글을 수정, 삭제할 권한이 없습니다."),

    // POSTLIKE
    POST_LIKE_DUPLICATED(400, "PL001", "이미 게시글에 좋아요를 한 사용자입니다."),
    POST_LIKE__NOT_FOUND(404, "PL002", "게시글의 좋아요를 찾을 수 없습니다."),
    ;

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
