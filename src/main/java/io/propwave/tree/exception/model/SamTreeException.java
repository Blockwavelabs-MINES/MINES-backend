package io.propwave.tree.exception.model;

import io.propwave.tree.exception.Error;
import lombok.Getter;

@Getter
public class SamTreeException extends RuntimeException {

    private final Error error;

    public SamTreeException(Error error) {
        super(error.getMessage());
        this.error = error;
    }
}
