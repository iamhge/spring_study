package com.example.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa") // 모든 메소드의 Prefix 설정
public class UserJpaController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        // 데이터가 존재하지 않을 경우도 있으므로 Optional 타입으로 데이터를 받는다.
        Optional<User> user = userRepository.findById(id);

        // user 안에 데이터가 존재하지 않는다면
        if (!user.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // HATEOAS
        // 반환시켜주고자하는 user 객체에 retrieveAllUsers(전체 user 보기) 링크를 추가한다.
        EntityModel<User> resource = new EntityModel<>(user.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);

        // Response의 Header에서 확인 가능하다.
        URI location = ServletUriComponentsBuilder.fromCurrentRequest() // 현재 request path에서
                .path("/{id}") // "/{id}"를 추가로 넣는다.
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable int id) {
        // 데이터가 존재하지 않을 경우도 있으므로 Optional 타입으로 데이터를 받는다.
        Optional<User> user = userRepository.findById(id);

        // user 안에 데이터가 존재하지 않는다면
        if (!user.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<User> createPost(@PathVariable int id, @RequestBody Post post) {
        // 사용자 정보를 검색해서, 해당 정보의 id값을 post에 지정해야한다.
        Optional<User> user = userRepository.findById(id);

        // user 안에 데이터가 존재하지 않는다면
        if (!user.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        post.setUser(user.get());
        Post savedPost = postRepository.save(post);

        // 생성된 post 정보의 uri값을 response로 보낸다.
        // Response의 Header에서 확인 가능하다.
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
