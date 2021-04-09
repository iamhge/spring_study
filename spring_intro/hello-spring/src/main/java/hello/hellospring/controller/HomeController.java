package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 정적 컨텐츠는 관련된 spring controller가 있는지 확인 후, 없으면 static 파일을 찾는다.
// '/'에 mapping controller가 있으므로 index.html은 무시된다.
@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
