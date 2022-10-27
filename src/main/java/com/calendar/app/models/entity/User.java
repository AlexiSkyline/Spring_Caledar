package com.calendar.app.models.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

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
    @Size(  min = 6, max = 16 )
    public String password;
    @ManyToMany( fetch = LAZY )
    @JoinTable( name = "user_roles", joinColumns = @JoinColumn( name = "user_id" ),
        inverseJoinColumns = @JoinColumn( name = "role_id" ))
    public Set<Role> roles = new HashSet<>();

    public User( String username, String email, String password ) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User( String username, String email, String password, Set<Role> roles ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}