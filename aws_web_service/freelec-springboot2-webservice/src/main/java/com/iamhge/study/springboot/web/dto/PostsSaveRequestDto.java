// p107
package com.iamhge.study.springboot.web.dto;

import com.iamhge.study.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Entity 클래스와 거의 유사한 형태임에도 Dto 클래스를 추가로 생성한다.
// 절대로 Entity 클래스를 Request/Response 클래스로 사용해서는 안된다.
// Entity 클래스는 데이터베이스와 맞닿은 핵심 클래스이다.
// 사소한 기능 변경을 위해 테이블과 연결된 Entity 클래스를 변경하는 것은 너무 큰 변경.
// Request/Response 용 Dto는 View를 위한 클래스라 정말 자주 변경이 필요.
// View Layer와 DB Layer의 역할 분리를 철저하게 해야 한다.
@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
