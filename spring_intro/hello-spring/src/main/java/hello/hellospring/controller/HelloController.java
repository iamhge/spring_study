package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("hello-mvc")
    // http://localhost:8080/hello-mvc?name=spring
    // 위와 같이 name을 보내면 파라미터가 전달된다.
    public String helloMvc(@RequestParam(name = "name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    // html이 아닌, 그대로 데이터를 넣어줌.
    // http://localhost:8080/hello-string?name=spring
    // 위와 같이 쳐서 들어간 후 page source를 보면 html tag가 아닌 문자 내용을 직접 반환한 것 확인 가능
    @GetMapping("hello-string")
    // @ResponseBody : http에서 응답 body부의 데이터를 직접 넣어주겠다.
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // ex) name을 spring이라 넣으면 -> "hello spring"
    }

    @GetMapping("hello-api")
    // 이전에는 viewResolver가 동작했으나, @ResponseBody 때문에 HttpMessageConverter를 이용
    // 이때, 객체이므로 JsonConverter가 동작. (cf. 단순 문자열의 경우 StringConverter 동작)
    @ResponseBody
    // 객체를 return 해야 함.
    // (default) json 방식으로 데이터를 만들어서 http에 반환함.
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;

        // property 접근 방식 (getter, setter)
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
