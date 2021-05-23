package com.example.restfulwebservices.user;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

// 아래의 method를 사용하기 위해서 앞에 WebMvcLinkBuilder라는 클래스명을 붙이지 않아도 된다.
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        // 단축키 ctrl+art+v
        User user = service.findOne(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // HATEOAS
        EntityModel<User> model = new EntityModel<>(user);
//        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(
//                WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        // 현재 클래스가 가지고 있는 retrieveAllUsers(전체 사용자 목록 보기) 메소드를
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        // all-users와 link 한다.
        model.add(linkTo.withRel("all-users"));

        return model;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        // fromCurrentRequest() : 현재 요청되어진 request값을 사용한다.
        URI location = ServletUriComponentsBuilder.fromCurrentRequest() // 사용자가 요청한 URI를 가져온다.
                .path("/{id}") // path를 통해 원하는 정보를 입력한다. (buildAndExpand로 얻은 값이 여기에 들어간다.)
                .buildAndExpand(savedUser.getId()) // 내가 원하는 값을 넣어주면 path에 추가되어 URI가 구성된다.
                .toUri();

        return ResponseEntity.created(location).build(); // ResponseEntity : 사용자에게 전달한다.
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = service.deleteById(id);
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
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
