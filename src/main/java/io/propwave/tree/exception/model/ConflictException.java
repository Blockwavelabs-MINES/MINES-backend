package io.propwave.tree.exception.model;

import io.propwave.tree.exception.Error;

public class ConflictException extends SamTreeException {

    public ConflictException(String message) {
        super(message, Error.CONFLICT_EXCEPTION);
    }
}
