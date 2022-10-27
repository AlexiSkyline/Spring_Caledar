package com.calendar.app.payload.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
    public static ResponseEntity<ResponseBody> responseBuild( HttpStatus httpStatus, String message, Object data ) {
        ResponseBody responseBody = new ResponseBody.ResponseBodyBuilder().setHttpCode( httpStatus.value() )
                .setHttpStatus( httpStatus ).setMessage( message ).setData( data ).build();

        return new ResponseEntity<>( responseBody, httpStatus );
    }
}