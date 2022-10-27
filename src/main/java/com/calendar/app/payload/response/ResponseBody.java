package com.calendar.app.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@AllArgsConstructor @Getter
public class ResponseBody {
    private final String timeStamp;
    private final int httpCode;
    private final HttpStatus httpStatus;
    private final String message;
    private final Object data;

    public static class ResponseBodyBuilder implements IBuilder<ResponseBody> {
        private int httpCode;
        private HttpStatus httpStatus;
        private String message;
        private Object data;

        public ResponseBodyBuilder setHttpCode( int httpCode ) {
            this.httpCode = httpCode;
            return this;
        }

        public ResponseBodyBuilder setHttpStatus( HttpStatus httpStatus ) {
            this.httpStatus = httpStatus;
            return this;
        }

        public ResponseBodyBuilder setMessage( String message ) {
            this.message = message;
            return this;
        }

        public ResponseBodyBuilder setData( Object data ) {
            this.data = data;
            return this;
        }

        @Override
        public ResponseBody build() {
            return new ResponseBody( new Date().toString(), this.httpCode, this.httpStatus, this.message, this.data );
        }
    }
}