package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

// @Transactional : test를 수행할 때는  DB에 데이터를 넣고(트랜잭션 시작하), test가 끝나면 commit하지 않고 rollback을 한다.
// -> db에 test용으로 넣었던 데이터가 db에 반영되지 않는다.고 -> 다음 test에 영향을 주지 않는다.
// cf. 이걸 안쓰면 test할 때 실제 db에 데이터를 넣어서 commit까지 해버린다. -> test 앞뒤로 데이터를 빼주는 과정이 필요해짐.
@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        // test 전용 db를 따로 구축하거나, local pc에 있는 db로 test하므로 실제 db가 변경되지는 않는다.
        member.setName("spring");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}