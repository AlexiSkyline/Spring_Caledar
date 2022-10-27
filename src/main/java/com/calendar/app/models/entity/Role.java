package com.calendar.app.models.entity;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table( name = "roles" )
@Getter
public class Role {
    @Id
    @GeneratedValue( strategy = IDENTITY )
    private Long id;
    @Enumerated( EnumType.STRING )
    @Column( length = 20 )
    private TypeRole name;
}