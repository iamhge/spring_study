package com.example.restfulwebservices.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;

@RestController
public class HelloWorldController {

    // 사용자로부터의 요청에 대한 결과를 처리하는 과정에서 발생하는 문자열을 각 언어별로 새로운
    // HTML페이지를 생성하지 않게 하기 위한 메시지 표시를 위한 객체
    // getNessage()라는 메소드에 필요한 key를 요청하게 되면 해당 Value를 message.properties파일에서 검색하여 반환
    // 이때 locale정보를 같이 전달할 수도 있으며,
    // 전달하지 않을 경우 LocaleResolver에서 설정한 기본 값을 사용하게 된다.
    @Autowired
    private MessageSource messageSource;

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

    // 다국어 처리
    @GetMapping(path = "/hello-world-internationalized")
    // required = false : 필수가 아니다
    // Accept-Language라는 헤더 값이 포함되지 않았을 경우 자동적으로 default locale 값, 즉 한국어값이 설정된다.
    public String helloWorldInternationalized(@RequestHeader(name="Accept-Language", required = false) Locale locale, HttpServletRequest request) {
        // 헤더 출력
        Enumeration<String> headers = request.getHeaderNames();
        Collections.list(headers).stream().forEach(name -> {
            Enumeration<String> values = request.getHeaders(name);
            Collections.list(values).stream().forEach(value -> System.out.println(name + "=" + value));
        });
        return messageSource.getMessage("greeting.message", null, locale);
    }
}
