package com.example.restfulwebservices.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
// service -> 비즈니스 로직
public class UserDaoService {
    // memory에 데이터 추가할 것이므로 list에 user를 넣는다.
    private static List<User> users = new ArrayList<>();
    private static int usersCount = 3;

    // users가 static으로 만들어진 변수이므로, static 블럭에 초기값 생성.
    static {
        users.add(new User(1,"Kenneth", new Date()));
        users.add(new User(2,"Alice", new Date()));
        users.add(new User(3,"Elena", new Date()));
    }

    // 사용자 추가
    public User save(User user) {
        // 전달 받은 user 객체에 id값이 존재하지 않으면
        if (user.getId() == null) {
            // 전체 list의 개수 + 1로 id값 설정
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    // 전체 사용자 목록 반환
    public List<User> findAll() {
        return users;
    }

    // 개별 사용자 data 반환
    public User findOne(int id) {
        // Data를 검색할 수 있는가?
        for (User user: users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

}
