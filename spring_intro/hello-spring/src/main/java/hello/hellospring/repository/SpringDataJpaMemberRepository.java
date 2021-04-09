package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// spring data jpa가 JpaRepository를 받고 있으면 SpringDataJpaMemberRepository가 구현체를 자동으로 만들어준다.
// spring data jpa가 spring bean에 자동 등록해준다.
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    @Override
    Optional<Member> findByName(String name);
}