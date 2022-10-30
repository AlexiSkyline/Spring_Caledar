package com.calendar.app.controller;

import com.calendar.app.exception.FieldAlreadyUsedException;
import com.calendar.app.exception.RecordNotFountException;
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
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice @AllArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request ) {
        BindingResult result = ex.getBindingResult();
        List<Error> errors = new ArrayList<>();
        result.getFieldErrors().forEach( error -> {
            String message = this.messageSource.getMessage( error, Locale.forLanguageTag( "US" ) );
            errors.add( new Error( error.getField(), "JSON-Body", message ) );
        });
        ExceptionResponse responseBody = this.buildResponseBody( status, errors );

        return new ResponseEntity<>( responseBody, status );
    }

    @ExceptionHandler( value = { FieldAlreadyUsedException.class } )
    public ResponseEntity<ExceptionResponse> fieldAlreadyUsedException( FieldAlreadyUsedException exception ) {
        Error error = new Error( exception.getFieldName(), "JSON-Body", exception.getMessage() );
        ExceptionResponse responseBody = this.buildResponseBody( CONFLICT, Collections.singletonList( error ));

        return new ResponseEntity<>( responseBody, CONFLICT );
    }

    @ExceptionHandler( value = { RecordNotFountException.class } )
    public ResponseEntity<ExceptionResponse> recordNotFountException( RecordNotFountException exception ) {
        Error error = new Error( exception.getFieldName(), "PathVariable", exception.getMessage() );
        ExceptionResponse responseBody = this.buildResponseBody( NOT_FOUND, Collections.singletonList( error ));

        return new ResponseEntity<>( responseBody, NOT_FOUND );
    }

    private ExceptionResponse buildResponseBody( HttpStatus status, List<Error> error ) {
        return new ExceptionResponse( new Date().toString(), status.value(), status, error );
    }
}