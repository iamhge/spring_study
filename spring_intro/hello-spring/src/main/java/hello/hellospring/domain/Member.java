package hello.hellospring.domain;

import javax.persistence.*;

// @Entity : jpa가 관리하는 entity
@Entity
public class Member {
    // IDENTITY 전략 : db에 값을 넣으면 db가 값을 자동으로 생성해주는 것.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 시스템이 정함

    private String name; // 고객이 회원가입 시 지정

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}