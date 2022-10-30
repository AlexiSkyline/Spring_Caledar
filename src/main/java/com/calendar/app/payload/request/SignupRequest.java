package com.calendar.app.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignupRequest {
    @NotBlank
    @Size( min = 5, max = 25 )
    private String username;
    @NotBlank
    @Size( max = 50 )
    @Email
    private String email;
    @NotBlank
    @Size( min = 6, max = 16 )
    private String password;
}