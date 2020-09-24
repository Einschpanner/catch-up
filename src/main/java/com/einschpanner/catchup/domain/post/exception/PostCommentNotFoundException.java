package com.einschpanner.catchup.domain.post.exception;

import com.einschpanner.catchup.global.error.ErrorCode;
import com.einschpanner.catchup.global.error.exception.BusinessException;

public class PostCommentNotFoundException extends BusinessException {
    public PostCommentNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND);
    }
}
