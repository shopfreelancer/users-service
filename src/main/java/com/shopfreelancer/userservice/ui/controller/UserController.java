package com.shopfreelancer.userservice.ui.controller;

import com.shopfreelancer.userservice.ui.model.request.UserDetailsRequestModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @GetMapping
    public String getUser(){
        return "user details";
    }

    @PostMapping
    public String createUser(@RequestBody UserDetailsRequestModel userDetails){
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
