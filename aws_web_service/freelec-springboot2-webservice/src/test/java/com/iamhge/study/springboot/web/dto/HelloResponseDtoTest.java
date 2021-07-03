// p73
package com.iamhge.study.springboot.web.dto;

import org.junit.Test;

// JUnit의 assertThat보다 asserj이 더 편리하다.
// 참고 : 백기선님의 유튜브 AssertJ가 JUnit의 assertThat 보다 편리한 이유 https://www.youtube.com/watch?v=zLx_fI24UXM
import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        // given
        String name = "test";
        int amount = 1000;

        // when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        // then
        // assertThat : assertj라는 테스트 검증 라이브러리의 검증 메소드
        // isEqualTo : assertj의 동등 비교 메소드
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
