package com.shopfreelancer.userservice.io.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity(name="users")
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 50)
    private String userId;
    private String firstName;
    private String lastName;

    @Column(nullable = false, length = 120, unique = true)
    private String email;
    private String encryptedPassword;
    private String emailVerificationToken;

    @Column(nullable = false)
    private Boolean emailVerificationStatus = false;
}
