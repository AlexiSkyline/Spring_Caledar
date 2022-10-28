package com.calendar.app.exception;

import lombok.Data;

@Data
public class Error {
    private final String message;
    private final String param;
    private final String location;
}