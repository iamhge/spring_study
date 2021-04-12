package org.springframework.samples.petclinic.owner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 이 annotation을 붙일 수 있는 대상을 지정.
@Retention(RetentionPolicy.RUNTIME) // 이 annotation 정보를 언제까지 유지할 것인지. (어느 시점까지 메모리를 가져갈 지)
public @interface LogExecutionTime {
}
