package com.calendar.app.exception;

import lombok.Data;

@Data
public class Error {
    private final String param;
    private final String location;
    private final String message;
}