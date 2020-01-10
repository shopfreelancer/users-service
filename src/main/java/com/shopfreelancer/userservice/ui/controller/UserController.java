package com.shopfreelancer.userservice.ui.controller;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.shopfreelancer.userservice.service.UserService;
import com.shopfreelancer.userservice.shared.dto.UserDto;
import com.shopfreelancer.userservice.ui.model.request.UserDetailsRequestModel;
import com.shopfreelancer.userservice.ui.model.response.UserRest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@Slf4j
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable String id) {
        UserRest userRest = new UserRest();

        UserDto userDto = userService.getUserById(id);
        BeanUtils.copyProperties(userDto, userRest);
        return userRest;
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
        UserRest userRest = new UserRest();
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userDetails, userDto);


        UserDto savedUserDto = userService.createUser(userDto);
        BeanUtils.copyProperties(savedUserDto, userRest);
        return userRest;

    }

    @PutMapping
    public String updateUser() {
        return "user updated";
    }

    @DeleteMapping
    public void deleteUser() {

    }
}
