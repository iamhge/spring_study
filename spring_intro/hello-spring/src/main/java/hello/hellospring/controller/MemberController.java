package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    private final MemberService memberService;

    // spring container에 등록을 하고 쓰면 하나만 쓸 수 있다.
    // 굳이 controller마다 하나씩 만들지 않고,
    // 다른 여러 controller에서 memberService를 같이 공용으로 쓸 수 있다.
    // @Autowired : spring이 memberService를 spring container에 있는 memberService에 연결시켜줌.
    // memberController가 생성될 때, 스프링 빈에 등록되어있는 memberService 객체를 넣어준다. (Dependency Injection)
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}