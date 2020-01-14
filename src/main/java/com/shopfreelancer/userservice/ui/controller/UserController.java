package com.shopfreelancer.userservice.ui.controller;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.shopfreelancer.userservice.exceptions.UserServiceException;
import com.shopfreelancer.userservice.service.UserService;
import com.shopfreelancer.userservice.shared.dto.UserDto;
import com.shopfreelancer.userservice.ui.model.request.UserDetailsRequestModel;
import com.shopfreelancer.userservice.ui.model.response.ErrorMessages;
import com.shopfreelancer.userservice.ui.model.response.UserRest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @GetMapping(path = "/{id}",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public UserRest getUser(@PathVariable String id) {
        UserRest userRest = new UserRest();

        UserDto userDto = userService.getUserById(id);
        BeanUtils.copyProperties(userDto, userRest);
        return userRest;
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
        UserRest userRest = new UserRest();
        UserDto userDto = new UserDto();

        if(userDetails.getFirstName().isEmpty()){
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        BeanUtils.copyProperties(userDetails, userDto);


        UserDto savedUserDto = userService.createUser(userDto);
        BeanUtils.copyProperties(savedUserDto, userRest);
        return userRest;

    }

    @PutMapping(path = "/{id}")
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
        UserRest userRest = new UserRest();
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userDetails, userDto);

        UserDto savedUserDto = userService.updateUser(id, userDto);
        BeanUtils.copyProperties(savedUserDto, userRest);
        return userRest;
    }

    @DeleteMapping
    public void deleteUser() {

    }
}
