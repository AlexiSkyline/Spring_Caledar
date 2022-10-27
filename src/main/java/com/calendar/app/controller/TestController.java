package com.calendar.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api/test" )
public class TestController {
    @GetMapping( "/all" )
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping( "/user" )
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping( "/mod" )
    public String moderatorAccess() {
        return "Moderator Content.";
    }

    @GetMapping( "/admin" )
    public String adminAccess() {
        return "Admin Content.";
    }
}