package com.example.restfulwebservices.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor // 모든 field가 들어간 생성자 생성
@NoArgsConstructor // 매개변수가 없는 생성자 생성
public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String details;
}
