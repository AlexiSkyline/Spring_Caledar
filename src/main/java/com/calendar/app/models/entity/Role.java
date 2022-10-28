package com.calendar.app.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table( name = "roles" )
@NoArgsConstructor @Getter
public class Role {
    @Id
    @GeneratedValue( strategy = IDENTITY )
    private Long id;
    @Enumerated( EnumType.STRING )
    @Column( length = 20 )
    private TypeRole name;

    public Role( TypeRole name ) {
        this.name = name;
    }
}