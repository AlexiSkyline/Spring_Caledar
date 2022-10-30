package com.calendar.app.security.jwt;

import com.calendar.app.exception.Error;
import com.calendar.app.payload.response.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Component @Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    @Override
    public void commence( HttpServletRequest request, HttpServletResponse response, AuthenticationException authException ) throws IOException, ServletException {
        log.error( "Unauthorized error: {}", authException.getMessage() );
        response.setContentType( APPLICATION_JSON_VALUE );
        response.setStatus( SC_UNAUTHORIZED );

        Error error = new Error( "Credentials", "Authentication", authException.getMessage() );
        ExceptionResponse responseBody = new ExceptionResponse( new Date().toString(), UNAUTHORIZED.value(),
                UNAUTHORIZED, Collections.singletonList( error ) );

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue( response.getOutputStream(), responseBody );
    }
}