package com.shopfreelancer.userservice.service;

import com.shopfreelancer.userservice.shared.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);
}
