package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    // Get : http GET method
    // hello라는 url에 매칭시킨다.
    @GetMapping("hello")
    // Spring에서 model을 만들어 넣어 메소드를 실행시킨다.
    public String hello(Model model) {
        // hello.html파일에서, data를 hello!!로 치환한다.
        // key : data, value : hello!!
        model.addAttribute("data", "hello!!");

        // resources의 templates에 있는 hello를 찾아가서 렌더링해라. (Thymeleaf 템플릿 엔진이 처리)
        return "hello";
    }
}