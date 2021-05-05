package com.example.restfulwebservices.user;

import lombok.AllArgsConstructor;
import lombok.Data;

// ※주의※
// spring boot ver 2.3.0부터 web에 dependency로
// spring-boot-starter-validation이 제외되어 별도로 추가해줘야 한다. (pom.xml에 직접 추)
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import java.util.Date;

@Data
@AllArgsConstructor
public class User {
    private Integer id;

    @Size(min=2, message="Name은 2글자 이상 입력해 주세요.")
    private String name;
    @Past
    private Date joinDate;
}
