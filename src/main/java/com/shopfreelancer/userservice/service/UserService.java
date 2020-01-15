package com.shopfreelancer.userservice.service;

import com.shopfreelancer.userservice.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto updateUser(String userId, UserDto userDto);
    UserDto getUser(String email);
    UserDto getUserById(String id);
    void deleteUser(String userId);
    List<UserDto> getUsers(int page, int limit);
    Long countUsers();
}
