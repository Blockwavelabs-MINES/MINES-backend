package io.propwave.tree.exception.model;

import io.propwave.tree.exception.Error;
import lombok.Getter;

@Getter
public class SamTreeException extends RuntimeException {

    private final Error error;
    private final String message;

    public SamTreeException(String message, Error error) {
        super(message);
        this.error = error;
        this.message = message;
    }
}
