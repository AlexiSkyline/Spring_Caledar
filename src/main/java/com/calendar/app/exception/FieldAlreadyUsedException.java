package com.calendar.app.exception;

import lombok.Getter;

@Getter
public class FieldAlreadyUsedException extends RuntimeException {
    private final String fieldName;
    private final String fieldValue;
    private final String modelName;

    public FieldAlreadyUsedException( String fieldName, String fieldValue, String modelName ) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.modelName = modelName;
    }

    @Override
    public String getMessage() {
        return String.format( "The '%s' field with the value '%s', is already in use with another '%s'.",
                this.fieldName, this.fieldValue, this.modelName );
    }
}