// p132, p138, p150, p190
package com.iamhge.study.springboot.web;

import com.iamhge.study.springboot.config.auth.dto.SessionUser;
import com.iamhge.study.springboot.service.posts.PostsService;
import com.iamhge.study.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

// 페이지에 관련된 컨트롤러는 모두 IndexController를 사용.
@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        // (SessionUser) httpSession.getAttribute("user")
        // * CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장하도록 구성함.
        // * 즉, 로그인 성공 시 httpSession.getAttribute("user")에서 값을 가져올 수 있다.
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        // if (user != null)
        // * 세션에 저장된 값이 있을 때만 model에 userName으로 등록한다.
        // * 세션에 저장된 값이 없으면 model엔 아무런 값이 없는 상태이니 로그인 버튼이 보이게 된다.
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        // 머스테치 스타터 덕분에 컨트롤러에서 문자열을 반환할 때 앞의 경로와 뒤의 파일 확장자는 자동으로 지정된다.
        // 앞 : src/main/resources/templates
        // 뒤 : .mustache
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }
}
