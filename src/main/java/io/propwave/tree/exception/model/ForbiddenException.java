package io.propwave.tree.exception.model;

import io.propwave.tree.exception.Error;

public class ForbiddenException extends SamTreeException {

    public ForbiddenException(String message) {
        super(message, Error.FORBIDDEN_EXCEPTION);
    }
}
