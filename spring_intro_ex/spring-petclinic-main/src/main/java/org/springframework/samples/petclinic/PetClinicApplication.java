/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * PetClinic Spring Boot Application.
 *
 * @author Dave Syer
 *
 */
// @SpringBootApplication 안에 @ComponentScan 이라는 annotation이 있다.
// 이 annotation이 어디부터 component를 찾아보라고 알려줌.
// @ComponentScan이 붙어있는 위치에서부터 모든 하위 패키지에 있는 클래스를 찾아서 bean으로 등록한다.
@SpringBootApplication(proxyBeanMethods = false)
public class PetClinicApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetClinicApplication.class, args);
	}

}
