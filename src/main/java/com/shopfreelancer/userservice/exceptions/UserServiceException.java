package com.shopfreelancer.userservice.exceptions;

public class UserServiceException extends RuntimeException {
    private static final long serialVersionUID = 8549446229010152178L;

    public UserServiceException(String message) {
        super(message);
    }
}
