package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// 서비스는 비즈니스 롤에 의존적으로 설계
// repository는 단순히 데이터의 이동 등에 맞게 이름을 설계

// @Service : spring이 올라올 때 container에 memberService를 등록시킨다.
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    // <DI(Dependency Injection)>
    // memberRepository를 내부에서 정의하면,
    // ex) private final MemberRepository memberRepository = new MemoryMemberRepository();
    // memberService에 이미 정의된 하나의 memberRepository와 밖에서 정의된 서로 다른 instance까지 총 두 개가 생긴다.
    // 따라서 memberRepository를 외부에서 직접 넣어줄 수 있게 한다.
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /* 회원 가입 */
    public Long join(Member member) {
        // 중복 회원 검증 방법 3가지
        // 방법 1)
        /*
        // 앞부분 자동 완성 단축키) ctrl + art + v
        Optional<Member> result = memberRepository.findByName(member.getName());


        // ifPresent : Optional이기 때문에 가능한 방법
        result.ifPresent(m -> { // result null이 아니고 값이 있으면,
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
        */

        // 방법 2)
        /*
        // 방법 1처럼 하면 Optional<Member> 붙고, 보기 안좋으니까 방법 2와 같이 줄일 수 있다.
        // 근데 또 사실 이 방법은 method로 따로 뽑는게 나음.
        // refactoring 단축키) ctrl + shift + art + t
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                })
        */

        // 방법 3) extract method
        validateDuplicateMember(member);

        // 중복 회원 없다면 save
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /* 전체 회원 조회 */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
