package com.calendar.app.security.jwt;

import com.calendar.app.security.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Component @Slf4j
public class JwtUtils {
    @Value( "${jwt.secret.word}" )
    private String SECRET_WORD;
    @Value( "${jwt.expiration.minutes}" )
    private int EXPIRATION_JWT_MINUTES;

    public String generateJwtToken( Authentication authentication ) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject( userDetails.getUsername() )
                .setIssuedAt( new Date() )
                .setExpiration( new Date(( new Date() ).getTime() + ( EXPIRATION_JWT_MINUTES * 60000L ) ))
                .signWith( HS512, SECRET_WORD )
                .compact();
    }

    public String getUserNameFromJwtToken( String token ) {
        return Jwts.parser().setSigningKey( SECRET_WORD ).parseClaimsJws( token ).getBody().getSubject();
    }

    public boolean validateJwt( String authToken ) {
        try {
            Jwts.parser().setSigningKey( SECRET_WORD ).parseClaimsJws( authToken );
            return true;
        } catch ( SignatureException e ) {
            log.error( "Invalid JWT signature: {}", e.getMessage() );
        } catch ( MalformedJwtException e ) {
            log.error( "Invalid JWT token: {}", e.getMessage() );
        } catch ( ExpiredJwtException e ) {
            log.error( "JWT token is expired: {}", e.getMessage() );
        } catch ( UnsupportedJwtException e ) {
            log.error( "JWT token is unsupported: {}", e.getMessage() );
        } catch ( IllegalArgumentException e ) {
            log.error( "JWT claims string is empty: {}", e.getMessage() );
        }

        return false;
    }
}