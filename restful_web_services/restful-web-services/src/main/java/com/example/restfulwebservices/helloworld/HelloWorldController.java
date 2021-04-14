package com.example.restfulwebservices.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    // GET
    // URI : /hello-world (endpoint)
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    // GET 요청 보내보면, json 형태로 반환되는 것을 확인할 수 있다.
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World");
    }

    // ex) http://localhost:8088/hello-world-bean/path-variable/Kenneth
    // {name} : 클라이언트가 호출할 때, name을 직접 호출하는 것이 아니라 {name}을 통해 특정 변수로 지정한다.
    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    // @PathVariable : 이 파라미터 값이 path variable에 사용되는 변수 값임을 나타낸다.
    // if) path의 {변수명}과 파라미터의 변수명이 다르다면 @PathVariable(value="변수명") 이라고 지정한다.
    public HelloWorldBean helloWorldBean(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }
}
