package com.calendar.app.exception;

import lombok.Getter;

@Getter
public class RecordNotFountException extends RuntimeException {
    private final String tableName;
    private final String fieldName;
    private final String dataValue;

    public RecordNotFountException( String tableName, String fieldName, String dataValue ) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.dataValue = dataValue;
    }

    @Override
    public String getMessage() {
        return String.format("Not Found Any %s With The %s:'%s'", this.tableName, this.fieldName, this.dataValue );
    }
}