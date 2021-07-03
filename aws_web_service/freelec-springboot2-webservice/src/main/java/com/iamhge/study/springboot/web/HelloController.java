// p60
package com.iamhge.study.springboot.web;

import com.iamhge.study.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// @RestController
// * 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어준다.
// * 예전에는 @ResponseBody를 각 메소드마다 선언했던 것을 한번에 사용할 수 있게 해주는 것.
@RestController
public class HelloController {
    // p75
    @GetMapping("/hello/dto")
    // @RequestParam : 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }

    // @GetMapping
    // * HTTP 메소드인 GET의 요청을 받을 수 있는 API를 만들어 준다.
    // * 예전에는 @RequestMapping(method = RequestMethod.GET)으로 사용되었다.
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
