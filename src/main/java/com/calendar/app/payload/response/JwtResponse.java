package com.calendar.app.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class JwtResponse {
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private final String tokenType = "Bearer";
    private String token;

    public JwtResponse( Long id, String username, String email, List<String> roles, String accessToken ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.token = accessToken;
    }
}