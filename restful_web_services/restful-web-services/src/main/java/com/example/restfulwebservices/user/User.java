package com.example.restfulwebservices.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

// ※주의※
// spring boot ver 2.3.0부터 web에 dependency로
// spring-boot-starter-validation이 제외되어 별도로 추가해줘야 한다. (pom.xml에 직접 추가)
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
// @JsonIgnoreProperties(value={"password", "ssn"})
@NoArgsConstructor // default 생성자 자동 생성
//@JsonFilter("UserInfo") // UserController 쓰기 위해 우선 주석처리
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    @Size(min=2, message="Name은 2글자 이상 입력해 주세요.")
    @ApiModelProperty(notes = "사용자 이름을 입력해 주세요.")
    private String name;
    @Past
    @ApiModelProperty(notes = "사용자 등록일을 입력해 주세요.")
    private Date joinDate;

//    @JsonIgnore
    @ApiModelProperty(notes = "사용자 패스워드를 입력해 주세요.")
    private String password;
//    @JsonIgnore
    @ApiModelProperty(notes = "사용자 주민번호를 입력해 주세요.")
    private String ssn;

    // Post 데이터를 1:N으로 매칭 할  있다.
    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    // posts를 추가하면서, UserDaoService에서 생성자를 통해 초기 데이터를 넣을 때 오류가 발생한다.
    // 기존에는 AllArgsConstructor annotation으로 생성자를 사용했기 때문이다.
    // 따라서 posts를 제외한 다른 args들만 입력해서 객체를 생성할 수 있도록 한다.
    public User(int id, String name, Date joinDate, String password, String ssn) {
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
        this.password = password;
        this.ssn = ssn;
    }
}
