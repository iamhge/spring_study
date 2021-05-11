package com.example.restfulwebservices.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http에 h2-console 에 어떠한 요청이 들어와도 모두 통과된다.
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
        // http에 크로스 사이트 스크립트를 사용하지 않는다.
        http.csrf().disable();
        // 헤더에서 프레임에 관련된 속성값을 사용하지 않는다.
        http.headers().frameOptions().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("kenneth")
                .password("{noop}test1234") // {noop} : 어떠한 동작(인코딩)없이 사용한다.
                .roles("USER");
    }
}
