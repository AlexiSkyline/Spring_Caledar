package com.calendar.app.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table( name = "events" )
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Event {
    @Id
    @GeneratedValue( strategy = IDENTITY )
    private Long id;
    @NotNull
    @Size( min = 4, max = 100 )
    private String title;
    private String notes;
    @NotNull
    private Date start;
    @NotNull
    private Date end;
    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "id_user" )
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private User user;
}