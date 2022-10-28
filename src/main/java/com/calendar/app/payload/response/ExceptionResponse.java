package com.calendar.app.payload.response;

import com.calendar.app.exception.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@AllArgsConstructor @Getter
public class ExceptionResponse {
    private final String timeStamp;
    private final int httpCode;
    private final HttpStatus httpStatus;
    private final List<Error> errors;
}