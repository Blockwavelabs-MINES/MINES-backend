package io.propwave.tree.exception.model;

import io.propwave.tree.exception.Error;

public class NotFoundException extends SamTreeException {
    public NotFoundException(String message) {
        super(message, Error.NOT_FOUND_EXCEPTION);
    }
}
