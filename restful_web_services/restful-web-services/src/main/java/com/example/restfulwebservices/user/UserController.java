package com.example.restfulwebservices.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private UserDaoService service;

    // DI(의존성 주입)
    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    // http를 통해서 request되는 data들은 모두 String 형태이다.
    // 파라미터에서 String이라고 선언하지 않고 int라고 선언하면, String이 자동으로 int로 converting 된다.
    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        return service.findOne(id);
    }
}
