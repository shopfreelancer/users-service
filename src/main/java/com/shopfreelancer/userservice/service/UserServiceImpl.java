package com.shopfreelancer.userservice.service;

import com.shopfreelancer.userservice.io.entity.UserEntity;
import com.shopfreelancer.userservice.io.repository.UserRepository;
import com.shopfreelancer.userservice.shared.Utils;
import com.shopfreelancer.userservice.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private Utils utils;

    public UserServiceImpl(UserRepository userRepository, Utils utils) {
        this.userRepository = userRepository;
        this.utils = utils;
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        UserEntity userExists = userRepository.findByEmail(userDto.getEmail());

        if(userExists != null){
            throw new RuntimeException("Email already exists");
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);

        userEntity.setEncryptedPassword("encryptedtestpassword");
        String publicUserId = utils.generateUserId(30);
        userEntity.setUserId(publicUserId);

        UserEntity savedUser = userRepository.save(userEntity);

        UserDto returnUser = new UserDto();
        BeanUtils.copyProperties(savedUser, returnUser);
        return returnUser;
    }
}
