package com.shopfreelancer.userservice.service;

import com.shopfreelancer.userservice.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto updateUser(String userId, UserDto userDto);
    UserDto getUser(String email);
    UserDto getUserById(String id);
    Long countUsers();
}
