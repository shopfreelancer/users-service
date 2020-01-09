package com.shopfreelancer.userservice.ui.controller;

import com.shopfreelancer.userservice.ui.model.request.UserDetailsRequestModel;
import com.shopfreelancer.userservice.ui.model.response.UserRest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @GetMapping
    public String getUser(){
        return "user details";
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails){
        return null;
    }

    @PutMapping
    public String updateUser(){
        return "user updated";
    }

    @DeleteMapping
    public void deleteUser(){

    }
}
