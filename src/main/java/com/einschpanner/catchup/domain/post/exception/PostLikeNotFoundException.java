package com.einschpanner.catchup.domain.post.exception;

import com.einschpanner.catchup.global.error.ErrorCode;
import com.einschpanner.catchup.global.error.exception.BusinessException;

public class PostLikeNotFoundException extends BusinessException {

    public PostLikeNotFoundException() {
        super(ErrorCode.POST_LIKE__NOT_FOUND);
    }
}