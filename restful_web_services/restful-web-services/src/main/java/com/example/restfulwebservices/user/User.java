package com.example.restfulwebservices.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ※주의※
// spring boot ver 2.3.0부터 web에 dependency로
// spring-boot-starter-validation이 제외되어 별도로 추가해줘야 한다. (pom.xml에 직접 추가)
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import java.util.Date;

@Data
@AllArgsConstructor
// @JsonIgnoreProperties(value={"password", "ssn"})
@NoArgsConstructor // default 생성자 자동 생성
@JsonFilter("UserInfo")
public class User {
    private Integer id;

    @Size(min=2, message="Name은 2글자 이상 입력해 주세요.")
    private String name;
    @Past
    private Date joinDate;

//    @JsonIgnore
    private String password;
//    @JsonIgnore
    private String ssn;
}
