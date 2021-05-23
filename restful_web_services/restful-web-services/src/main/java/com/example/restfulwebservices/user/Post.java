package com.example.restfulwebservices.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity // Jpa Entity 등록
@Data // lombok 사용
@AllArgsConstructor // 모든 필드를 다 가지고 있는 생성자 추가
@NoArgsConstructor // default 생성자 추가
public class Post {
    @Id // 기본키
    @GeneratedValue// 자동으로 생성될 수 있다.
    private Integer id;

    private String description;

    // 한 명의 사용자가 여러 개의 글을 작성할 수 있다.
    // User : Post -> 1 : (0~N) -> main : sub -> parent : child
    // @ManyToOne : Post라는 데이터가 여러 개 올 수 있고, 하나의 값과 매칭될 수 있다.
    // LAZY : 지연 로딩 방식. 사용자 entity를 조회할 때 매번 post entity가 같이 로딩되는 것이 아닌,
    // post 데이터가 로드되는 시점에 필요한 사용자 데이터를 가지고 온다.
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore // 외부에 데이터를 노출하지 않는다.
    private User user;
}
