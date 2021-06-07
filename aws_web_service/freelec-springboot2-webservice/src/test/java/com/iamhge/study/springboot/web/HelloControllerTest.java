// p61
package com.iamhge.study.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)// 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자 실행
@WebMvcTest(controllers = HelloController.class) // Web(Spring Mvc)에 집중할 수 있는 어노테이션
public class HelloControllerTest {

    @Autowired // 빈을 주입받는다.
    private MockMvc mvc; // 스프링 MVC 테스트의 시작점

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) // /hello 주소로 HTTP GET 요청
                .andExpect(status().isOk()) // 결과 검증. Http Header의 Status가 200인지 아닌지
                .andExpect(content().string(hello)); // 결과(응답 본문) 검증. Controller의 return 값이 "hello"이므로, 이 값이 맞는지
    }

    // p75
    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        // param : API 테스트 시 사용될 요청 파라미터 설정. 값은 String만 허용
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                // jsonPath : JSON 응답값을 필드별로 검증할 수 있는 메소드
                // $를 기준으로 필드명 명시
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }

}

// test 실행 안될 경우 intellij의 runner 변경.
// 참고 : https://handr95.tistory.com/17