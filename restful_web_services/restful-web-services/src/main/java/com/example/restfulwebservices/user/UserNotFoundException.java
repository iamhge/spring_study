package com.example.restfulwebservices.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        // super() : 부모 클래스의 생성자를 호출하는 메서드
        // 여기에서는 부모 클래스 = RuntimeException
        super(message);
    }
}
