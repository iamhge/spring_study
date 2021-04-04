package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

// 메소드 하나에 대한 test가 끝난 후 data를 clear해주지 않았을 때,
// 전체 class에 대해서 test 실행시켜보면 에러가 발생한다.
// 모든 test는 순서가 보장되지 않고 실행된다.
// 따라서 test는 서로간의 순서에 의존하지 않고 설계해야한다.
class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    // @AfterEach : 메소드 하나가 끝날 때마다 동작
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        // given
        Member member = new Member();
        member.setName("spring");

        // when
        repository.save(member);

        // then
        // get() : Optional에서 값을 꺼냄.
        Member result = repository.findById(member.getId()).get();
        // <검증 방법>
        // 방법 1)
        // System.out.println("(result == member) = " + (result == member));
        // 방법 2)
        // Assertions.assertEquals(member, result); // 출력 없으면 맞는 것.
        // 방법 3)
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // when
        Member result = repository.findByName("spring1").get();

        // then
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // when
        List<Member> result = repository.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
    }
}
