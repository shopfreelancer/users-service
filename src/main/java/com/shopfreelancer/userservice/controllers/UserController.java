package com.shopfreelancer.userservice.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @GetMapping
    public String getUser(){
        return "user details";
    }

    @PostMapping
    public String createUser(){
        return "user created";
    }

    @PutMapping
    public String updateUser(){
        return "user updated";
    }

    @DeleteMapping
    public void deleteUser(){

    }
}
