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

    /*
    <DI의 3가지 방법>
    1. 필드 주입
    * 별로 안좋음.
      중간에 바꿀 수 있는 방법이 아예 없어서.
    @Autowired private MemberService memberService;
    2. setter 주입
    * 중간에 memberService를 바꿀 일이 없음에도 public하게 노출이 된다.
    * 호출되지 않아야할 메서드이므로 크리티컬한 문제임.
    @Autowired
    public setMemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    3. 생성자 주입
    * 처음에 spring container올라가는 시점 한번에만 실행되므로 좋다.
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    */
}