// p94
package com.iamhge.study.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// @Repository annotation을 추가할 필요도 없다.
// Entity 클래스와 기본 Entity Repository는 함께 위치해야 한다.
// Entity 클래스는 기본 Repository 없이는 제대로 역할을 할 수 없다.
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
