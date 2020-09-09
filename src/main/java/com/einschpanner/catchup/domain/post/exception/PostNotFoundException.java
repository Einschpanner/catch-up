package com.einschpanner.catchup.domain.post.exception;

import com.einschpanner.catchup.global.error.exception.EntityNotFoundException;

public class PostNotFoundException extends EntityNotFoundException {

    public PostNotFoundException(String msg) {
        super(msg);
    }
}