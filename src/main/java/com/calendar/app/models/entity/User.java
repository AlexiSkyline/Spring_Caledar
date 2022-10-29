package com.calendar.app.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table( name = "users" )
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class User {
    @Id
    @GeneratedValue( strategy = IDENTITY )
    private Long id;
    @NotBlank
    @Size( min = 5, max = 25 )
    @Column( unique = true )
    private String username;
    @NotBlank
    @Size( max = 50 )
    @Email
    @Column( unique = true )
    private String email;
    @NotBlank
    @Size( max = 120 )
    @JsonIgnore
    public String password;
    @OneToOne( fetch = LAZY )
    @JoinColumn( name = "id_role" )
    @JsonIgnore
    public Role role;

    public User( String username, String email, String password ) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User( String username, String email, String password, Role roles ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = roles;
    }
}