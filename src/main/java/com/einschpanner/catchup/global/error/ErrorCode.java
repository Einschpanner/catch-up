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

    // Ex) Post
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
