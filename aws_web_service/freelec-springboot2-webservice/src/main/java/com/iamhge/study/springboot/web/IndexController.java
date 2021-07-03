// p132, p138, p150, p190, p195, p199
package com.iamhge.study.springboot.web;

import com.iamhge.study.springboot.config.auth.LoginUser;
import com.iamhge.study.springboot.config.auth.dto.SessionUser;
import com.iamhge.study.springboot.service.posts.PostsService;
import com.iamhge.study.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 페이지에 관련된 컨트롤러는 모두 IndexController를 사용.
@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    // 반복되는 부분들을 @LoginUser로 개선
    // private final HttpSession httpSession;

    @GetMapping("/")
    // 반복되는 부분들을 @LoginUser로 개선
    // @LoginUser SessionUser user
    // * 기존에 (User) httpSession.getAttribute("user")로 가져오던 세션 정보 값이 개선되었다.
    // * 이제는 어느 컨트롤러든지 @LoginUser만 사용하면 세션 정보를 가져올 수 있다.
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());
        // (SessionUser) httpSession.getAttribute("user")
        // * CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장하도록 구성함.
        // * 즉, 로그인 성공 시 httpSession.getAttribute("user")에서 값을 가져올 수 있다.
        // 이 코드의 단점
        // : index 메소드 외에 다른 컨트롤러와 메소드에서 세션값이 필요하면 그 때마다 직접 세션에서 값을 가져와야 한다.
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        // so 위 부분을 메소드 인자로 세션값을 바로 받을 수 있도록 변경한다. -> @LoginUser

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
