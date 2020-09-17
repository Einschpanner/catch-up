package com.einschpanner.catchup.domain.post.exception;

import com.einschpanner.catchup.global.error.ErrorCode;
import com.einschpanner.catchup.global.error.exception.BusinessException;

public class PostLikeDuplicatedException extends BusinessException {
    public PostLikeDuplicatedException() {
        super(ErrorCode.POST_LIKE_DUPLICATED);
    }
}
