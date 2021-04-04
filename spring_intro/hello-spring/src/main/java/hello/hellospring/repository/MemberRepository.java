package hello.hellospring.repository;


import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    // 회원을 저장소에 저장
    Member save(Member member);
    // Id, name 으로 회원을 찾음
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    // 저장된 모든 회원 리스트를 반환
    List<Member> findAll();
}

