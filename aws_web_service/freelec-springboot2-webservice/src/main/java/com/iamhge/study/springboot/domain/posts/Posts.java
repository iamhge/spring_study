// p88, p122
package com.iamhge.study.springboot.domain.posts;

import com.iamhge.study.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Posts : 실제 DB의 테이블과 매칭될 클래스
@Getter // 클래스 내 모든 필드의 Getter 메소드 자동생성
@NoArgsConstructor // 기본 생성자 자동 추가
@Entity // 테이블과 링크될 클래스임을 나타낸다.
public class Posts extends BaseTimeEntity {

    @Id // 해당 테이블의 PK필드를 나타낸다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성 규칙을 나타낸다.
    private Long id;

    // @Column : 테이블의 칼럼을 나타낸다. 굳이 안써도 해당 클래스의 필드는 모두 칼럼이긴함.
    // 기본값 외에 추가로 변경이 필요한 옵션이 있을 때 사용.
    @Column(length = 500, nullable = false) // String 기본 사이즈 VARCHAR(255) -> 500
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false) // 타입 String -> TEXT
    private String content;

    private String author;

    // @Builder : 해당 클래스의 빌더 패턴 클래스 생성
    // 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    // 빌더를 사용하게 되면 어느 필드에 어떤 값을 채워야할지 명확하게 인지 가능
    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
