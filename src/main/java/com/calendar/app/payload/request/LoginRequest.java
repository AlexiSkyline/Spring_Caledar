package com.calendar.app.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class LoginRequest {
    @NotBlank
    @Size( min = 5, max = 20 )
    @Column( unique = true )
    private String username;
    @NotBlank
    @Size(  min = 6, max = 16 )
    private String password;
}