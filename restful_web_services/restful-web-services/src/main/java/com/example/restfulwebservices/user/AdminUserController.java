package com.example.restfulwebservices.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

// 관리자만을 위한 controller
@RestController
@RequestMapping("/admin") // '/admin' 가 아래 메소드들을 사용하기 위한 공통 접두사가 되는 것.
public class AdminUserController {
    private UserDaoService service;

    // DI(의존성 주입)
    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers() {
        List<User> users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "password");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters); // filter를 적용

        return mapping;
    }

    // http를 통해서 request되는 data들은 모두 String 형태이다.
    // 파라미터에서 String이라고 선언하지 않고 int라고 선언하면, String이 자동으로 int로 converting 된다.
    @GetMapping("/users/{id}")
    public MappingJacksonValue retrieveUser(@PathVariable int id) {
        // 단축키 ctrl+art+v
        User user = service.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // filtering 기법을 이용해 전달하고자 하는 데이터 값을 변경
        // SimpleBeanPropertyFilter : properties를 제어한다.
        // 필드를 가지고 있는 클래스에 제어하고 싶은 객체가 있을 경우 filter 클래스를 사용해서 제어하면 쉽게 제어할 수 있다.
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "password", "ssn");

        // addFilter(<filter를 적용할 bean의 이름>, <filter>);
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters); // filter를 적용

        return mapping;
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> changeUser(@PathVariable int id, @RequestBody User user) {
        User changedUser = service.changeById(id, user);

        if (changedUser == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return ResponseEntity.accepted().build();
    }

}
