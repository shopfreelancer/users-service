package com.shopfreelancer.userservice.ui.controller;

import com.shopfreelancer.userservice.exceptions.UserServiceException;
import com.shopfreelancer.userservice.service.UserService;
import com.shopfreelancer.userservice.shared.dto.UserDto;
import com.shopfreelancer.userservice.ui.model.request.UserDetailsRequestModel;
import com.shopfreelancer.userservice.ui.model.response.ErrorMessages;
import com.shopfreelancer.userservice.ui.model.response.OperationStatusModel;
import com.shopfreelancer.userservice.ui.model.response.RequestOperationStatus;
import com.shopfreelancer.userservice.ui.model.response.UserRest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @DeleteMapping(path = "/{id}")
    public OperationStatusModel deleteUser(@PathVariable String id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @GetMapping
    public List<UserRest> getUsers(@RequestParam(value="page", defaultValue = "0") int page,
                                   @RequestParam(value="limit", defaultValue = "25") int limit){
        List<UserRest> returnList = new ArrayList<>();
        List<UserDto> users = userService.getUsers(page, limit);

        for(UserDto userDto : users){
            UserRest userModel = new UserRest();
            BeanUtils.copyProperties(userDto, userModel);
            returnList.add(userModel);
        }

        return returnList;
    }

    @GetMapping(path = "/email-verification")
    public OperationStatusModel verifyUserEmailAddress(@RequestParam(value = "token") String token){
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.VERIFY_EMAIL.name());

        boolean isVerified = userService.verifyEmailToken(token);

        return operationStatusModel;
    }
}
