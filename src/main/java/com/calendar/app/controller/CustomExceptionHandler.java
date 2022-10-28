package com.calendar.app.controller;

import com.calendar.app.exception.FieldAlreadyUsedException;
import com.calendar.app.payload.response.ExceptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.calendar.app.exception.Error;

import java.util.*;

import static org.springframework.http.HttpStatus.CONFLICT;

@RestControllerAdvice @AllArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request ) {
        BindingResult result = ex.getBindingResult();
        List<Error> errors = new ArrayList<>();
        result.getFieldErrors().forEach( error -> {
            String message = this.messageSource.getMessage( error, Locale.forLanguageTag( "US" ) );
            errors.add( new Error( message, error.getField(), "JSON-Body" ) );
        });
        ExceptionResponse responseBody = new ExceptionResponse( new Date().toString(), status.value(), status, errors );

        return new ResponseEntity<>( responseBody, status );
    }

    @ExceptionHandler( value = { FieldAlreadyUsedException.class } )
    public ResponseEntity<?> fieldAlreadyUsedException( FieldAlreadyUsedException exception ) {
        Error error = new Error( exception.getMessage(), exception.getFieldName(), "JSON-Body" );
        ExceptionResponse responseBody = new ExceptionResponse( new Date().toString(), CONFLICT.value(), CONFLICT, List.of( error ) );

        return new ResponseEntity<>( responseBody, CONFLICT );
    }
}