package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

// @Repository : spring이 올라올 때 가지고 올라옴 (controller, service, repository 를 모두 올림)
@Repository
public class MemoryMemberRepository implements MemberRepository {
    // cf. 실무) 공유되는 변수의 경우 ConcurrentHashMap 사용. (동시성 문제가 발생할 수 있어서)
    private static Map<Long, Member> store = new HashMap<>();
    // sequence : 키 값을 계속 생성함. ex) 1, 2, 3, ...
    private static long sequence = 0L;

    @Override
    // save하기 전, name은 넘어온 상태 (고객이 회원가입 시 적어서 넘김)
    public Member save(Member member) {
        // id 세팅
        member.setId(++sequence);
        // store에 저장
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // Optional.ofNullable() : store.get(id)이 null이어도 감싸서 반환할 수 있다.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny(); // findAny() : 하나라도 찾으면 반환. 없으면 Optional에 null 포함해서 반환.
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    // store를 싹 비움.
    public void clearStore() {
        store.clear();
    }
}
