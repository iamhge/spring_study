// p122, p221
package com.iamhge.study.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// p221
// @EnableJpaAuditing을 사용하기 위해선 최소 하나의 @Entity 클래스가 필요하다.
// @WebMvcTest에는 당연히 없다.
// EnableJpaAuditing가 @SpringBootApplication와 함께 있다 보니 @WebMvcTest에서도 스캔하게 되었다.
// 그래서 @EnableJpaAuditing과 @SpringBootApplication을 분리한다.-> config/JpaConfig에 이동

// p122
// @EnableJpaAuditing // JPA Auditing 활성화
// @SpringBootApplication : 스프링 부트의 자동 설저으 스프링 Bean 읽기와 생성을 모두 자동으로 설정
// @SpringBootApplication이 있는 위치부터 설정을 읽어간다. 따라서 이 클래스는 항상 프로젝트의 최상단에 위치해야 한다.
@SpringBootApplication
// 프로젝트의 메인 클래스
public class Application {
    public static void main(String[] args) {
        // SpringApplication.run : 내장 WAS 실행
        // * 내장 WAS : 별도로 외부에 WAS를 두지 않고 어플리케이션을 실행할 때 내부에서 WAS를 실행하는 것
        SpringApplication.run(Application.class, args);
    }
}
