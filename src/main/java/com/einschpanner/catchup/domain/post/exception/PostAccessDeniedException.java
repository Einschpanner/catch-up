package com.einschpanner.catchup.domain.post.exception;

import com.einschpanner.catchup.global.error.ErrorCode;
import com.einschpanner.catchup.global.error.exception.BusinessException;

public class PostAccessDeniedException extends BusinessException {
    public PostAccessDeniedException() {
        super(ErrorCode.POST_ACCESS_DENIED);
    }
}
