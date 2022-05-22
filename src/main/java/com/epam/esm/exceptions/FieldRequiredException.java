package com.epam.esm.exceptions;

public class FieldRequiredException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FieldRequiredException(String msg) {
        super(msg);
    }
}
