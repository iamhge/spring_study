package com.example.restfulwebservices;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldBean {
    private String message;

    // @AllArgsConstructor annotation에 의해 자동으로 생성된다.
    /*
    public HelloWorldBean(String message) {
        this.message = message;
    }
    */
}
