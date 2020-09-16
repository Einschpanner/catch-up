package com.einschpanner.catchup.domain.post.exception;

import com.einschpanner.catchup.global.error.ErrorCode;
import com.einschpanner.catchup.global.error.exception.BusinessException;

public class PostNotFoundException extends BusinessException {

    public PostNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND);
    }
}