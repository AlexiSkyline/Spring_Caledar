package com.calendar.app.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JwtResponse {
    private Long id;
    private String username;
    private String email;
    private String token;

    public JwtResponse( Long id, String username, String email, String accessToken ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.token = accessToken;
    }
}