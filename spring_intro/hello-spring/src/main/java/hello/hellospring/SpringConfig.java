package hello.hellospring;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.JdbcTemplateMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private DataSource dataSource;

    // spring boot가 application.properites(설정 파일)를 보고 자체적으로 bean을 생성해준다.
    // data source를 만들어서 주입을 해준다.
    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // spring이 올라오면서 Spring Bean에 등록시킨다.
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        // * memory
        // return new MemoryMemberRepository();

        // * jdbc
        // return new JdbcMemberRepository(dataSource);

        // * jdbc template
        return new JdbcTemplateMemberRepository(dataSource);
    }
}
