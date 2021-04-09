package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {
    // jpa는 EntityManager로 모든 것이 동작한다.
    // spring boot가 자동으로 EntityManager를 생성해준다. DB랑 연결해주고..
    // 그래서 우리는 만들어진 것을 injection(주입)해주면 된다.
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        // jpa가 insert query 만들어서 다 집어넣고,
        // setid까지 다 해준다.
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result =  em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // select m : 객체 자체를 select한다.
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
