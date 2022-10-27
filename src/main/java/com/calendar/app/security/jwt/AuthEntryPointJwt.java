package com.calendar.app.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Component @Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    @Override
    public void commence( HttpServletRequest request, HttpServletResponse response, AuthenticationException authException ) throws IOException, ServletException {
        log.error( "Unauthorized error: {}", authException.getMessage() );
        response.setContentType( APPLICATION_JSON_VALUE );
        response.setStatus( SC_UNAUTHORIZED );

        Map<String, Object> body = new HashMap<>();
        body.put( "status", SC_UNAUTHORIZED );
        body.put( "error", "Unauthorized" );
        body.put( "message", authException.getMessage() );
        body.put( "path", request.getServletPath() );

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue( response.getOutputStream(), body );
    }
}