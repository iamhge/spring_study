// p94, p146
package com.iamhge.study.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// @Repository annotation을 추가할 필요도 없다.
// Entity 클래스와 기본 Entity Repository는 함께 위치해야 한다.
// Entity 클래스는 기본 Repository 없이는 제대로 역할을 할 수 없다.
// but 안쓰면 자꾸 빨간줄 그어져서 나는 추가
@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {
    // SpringDataJpa에서 제공하지 않는 메소드는 쿼리로 작성해도 된다.
    @Query("select p from Posts p order by p.id desc")
    List<Posts> findAllDesc();
}
