package io.propwave.tree.exception.model;

import io.propwave.tree.exception.Error;

public class BadRequestException extends SamTreeException {

    public BadRequestException(String message) {
        super(message, Error.BAD_REQUEST_EXCEPTION);
    }
}
