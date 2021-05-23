# Spring Boot를 이용한 RESTful Web Services 개발
본 note는 인프런 '[Dowon Lee](https://www.inflearn.com/users/40572)'님의 '[Spring Boot를 이용한 RESTful Web Services 개발](https://www.inflearn.com/course/spring-boot-restful-web-services/dashboard)'을 수강하며 공부한 내용을 바탕으로 작성한 필기입니다.  

## 목차
0. [Web Service & Web Application](#0-web-service--web-application)
1. [Spring Boot로 개발하는 RESTful Service](#1-spring-boot로-개발하는-restful-service)
2. [User Service API 구현](#2-user-service-api-구현)
3. [RESTful Service 기능 확장](#3-restful-service-기능-확장)
4. [Spring Boot API 사용](#4-spring-boot-api-사용)
5. [Java Persistence API 사용](#5-java-persistence-api-사용)
6. [RESTful API 설계 가이드](#6-restful-api-설계-가이드)

## 0. Web Service & Web Application

### 0.1. Web Service와 Web Application

**Web Service**  
: 네트워크 상에서 서로 다른 종류의 컴퓨터들 간에 상호작용하기 위한 소프트웨어 시스템

**Web Service의 3가지 키워드**
* machine-to-machine(or application-to-application) 간의 상호작용을 위한 설계
* 플랫폼 독립적인 구조
* application간의 네트워크를 통한 통신

**Web Application**  
: 원격 서버에 저장되고, 브라우저 인터페이스를 통해 실행할 수 있는 프로그램

**Web browser**  
: http 프로토콜을 통해서 제공되는 html 문서를 분석해서 사용자에게 보여주는 application

**Web Application Server**  
: Web Application을 실행해 줄 수 있는 서버

**클라이언트의 요청을 처리해주는 방법**
- SOAP(Simple Object Access Protocol)  
  : http와 같은 프로토콜을 이용해서 xml에 기반해 메세지를 컴퓨터 네트워크를 통해 전달
- **RESTful**  
  : REST API를 제공하는 웹 서비스
  - REST(REpresentational State Transfer)
    - Resource의 Representation에 의한 상태(컴퓨터가 가지고 있는 자원의 상태 ex.file) 전달
    - http method를 통해 resource를 처리하기 위한 아키텍쳐
  - HTTP Methods : GET, PUT, POST, DELETE
  - HTTP Status Codes : 200, 404, ...
  - Resource 
    - URI(Uniform Resource Identifier)
    - XML, HTML, JSON

## 1. Spring Boot로 개발하는 RESTful Service

### 1.1. Spring Boot 개요

**Spring Boot**  
: 단독 실행 가능한 어플리케이션을 개발하기 위한 플랫폼
* 최소한의 설정만으로도 spring을 다룰 수 있다.
* 아래 사이트를 통해 만들 수 있으며, 개발에 필요한 템플릿 설정이 가능하다.  
  https://start.spring.io/

**실행 방법**
1) Spring Boot Application  
@SpringBootApplication annotation이 붙은 메인 클래스를 실행한다.

2) Auto Configuration  
첫번째로 실행되는 spring boot app에서 개발하고자하는 spring app에 필요한 각종 설정 작업을 자동화하고, 필요에 따라 개발자가 등록한
환경을 읽어온다.

3) Component Scan  
본 프로젝트에 선언된 각종 Component를 읽어온다.  
특정 클래스의 instance를 spring container의 메모리로 읽어와 app에서 사용할 수 있는 bean형태로 등록한다.  
ex) Service, Repository, Controller, Component

**IoC**  
: 개발자가 프로그래밍에 의해서 클래스의 instance를 생성하는 것이 아니라, spring에 의해서 생성되는 것

### 1.2. REST API 설계
[[spring initializr](https://start.spring.io/)]  
![image](https://user-images.githubusercontent.com/59961690/114547523-829a1f00-9c99-11eb-8f9d-2007244bda7a.png)  

* Group : spring 프로젝트를 통해 개발하고자 하는 app의 개발회사 이름 혹은 도메인 이름 등. 패키지 이름으로 사용된다.
* Artifact : Group에서 생성하고자하는 프로젝트의 종류. app의 이름
* Packaging : 개발 완료 후 패키징 시 어떠한 파일로 패키징할 것인지 선택
* Dependencies : spring boot 프로젝트에서 사용하고자 하는 라이브러리 지정

**실습을 통해 만들 RESTful services**  
* 시나리오 : 사용자를 관리하는 REST API와 그 사용자가 작성하는 블로그의 글
* 한 명의 사용자는 여러개의 포스팅이 가능하다. (User -> Posts)
* 조회, 생성, 삭제 기능

|Description|REST API|HTTP Method|
|---|---|---|
|Retrieve all Users|/users|GET|
|Create a User|/users|POST|
|Retrieve a User|/users/{id}|GET|
|Delete a User|/users/{id}|DELETE|
|Retrieve all posts for a User|/users/{id}/posts|GET|
|Create a post for a User|/users/{id}/posts|POST|
|Retrieve details of a User|/users/{id}/posts/{post_id}|GET|

### 1.3. Spring Boot Project 생성

**생성 방법**  
intelliJ를 통해 프로젝트 생성

**프로젝트 version 및 설정**  
|Java ver|Spring boot ver|Project Type|Packaging|
|---|---|---|---|
|11.0.9|2.4.4|Maven|jar|

**Dependency**  
* Developer Tools 
  * Spring Boot DevTools
  * Lombok
* Web
  * Spring Web
* SQL
  * Spring Data JPA
  * H2 Database

### 1.4. Spring Boot Project 구조 확인과 실행 방법

**Maven 명령어**  
* clean : 지금까지 build, packaging 시켰던 내용을 모두 지운다.
* compile : 작성한 프로젝트를 compile. 바이트 코드(class file)가 생성된다.
* package : compile 시킨 내용을 jar 혹은 war 파일로 패키징 시킨다.
* install : packaging 시킨 내용을 local 서버에 배포하는 작업을 실행한다.

**Spring project 실행**
* spring boot project 안에는 기본적으로 embedded tomcat이 내장되어 있다.  
  따라서 Spring boot에 Spring web dependency를 포함시킨 상태에서 app을 실행하면 java의 실행 method인 void main이 실행된다.
  그러면서 class 안쪽에서 app 실행에 필요한 embedded tomcat을 같이 실행하게 된다.
  * 별도의 web server를 구동하지 않은 상태에서 단순히 java program을 시켜 같이 서비스를 구동할 수 있다.

**pom.xml**  
: 전체 project에 필요한 maven 설정을 지정할 수 있다.

**application.properties**  
: Spring boot를 실행하면서 필요한 환경 설정을 지정할 수 있다.  
-> 실습에서는 application.yml로 변경함. (최근에는 yml파일이 더 많이 사용되는 추세)

### 1.5. HelloWorld Controller 추가

* REST API에서 제공하는 서비스들의 내용을 controller class 단위로 등록할 수 있다.
* REST API에서 자주 사용되는 controller class는 기존 spring MVC에서 사용되는 일반적인 controller class가 아니라, 
rest controller라는 class로 작동한다.

### 1.6. HelloWorld Bean 추가

**lombok**  
: bean 클래스를 만들 때, setter, getter, 생성자, toString, equals 메소드 등을 자동으로 생성해주는 기능이 포함되어있다. 
* @Data  
  : @Data annotation이 추가되면, 해당 class가 가진 모든 property, field에 대해서 setter, getter, 생성자, toString 메소드가 만들어진다.
* @AllArgsConstructor  
  : 모든 Arguments를 가지고 있는 Constructor가 만들어진다.  
* @NoArgsConstructor  
  : 매개변수가 아무것도 없는 default 생성자가 만들어진다.

> **※ lombok 라이브러리 사용 주의할 점 ※**  
> 
> * lombok 라이브러리를 사용하기 위해서는 intelliJ에서 아래 옵션을 켜주어야 한다.  
> Settings > Build, Execution, Deployment > Compiler > Annotation Processors 에서 Enable annotation processing을 활성화 시켜야한다.

* return type이 String값이 아닌 java bean 형태이면, 
spring framework에서는 단순한 text 혹은 object 형태가 아닌 json형태로 변환해서 반환한다.
* spring framework에 rest controller를 사용하게 되면, 반환 값을 
response body에 저장하지 않더라도 자동으로 json 문서로 반환한다.

### 1.7. DispatcherServlet과 프로젝트 동작의 이해

**설정파일**
- java class
- application.yml or application.properties
  - application.yml -> 설정이름:값  
    : 보다 직관적이다.
  - application.properties -> 설정이름=값

**DispatcherServlet**  
: 사용자의 요청을 처리해주는 Gate way  
- servlet container에서 http 프로토콜을 통해 들어오는 모든 요청값을 처리하기 위해 
presentation 계층의 제일 앞에 놓여지며, 중앙집중식의 요청을 처리하기 위한 front controller로서 사용된다.
- 사용자의 요청은 DispatcherServlet에서 시작해서 DispatcherServlet에서 끝난다.

[DispatcherServlet의 흐름]  
![image](https://user-images.githubusercontent.com/59961690/114712445-10920a80-9d6b-11eb-98fc-1134c83c2941.png)  
(이미지 출처 : https://mangkyu.tistory.com/18)  
1. 모든 사용자의 request가 DispatcherServlet에 전달
2. DispatcherServlet에서 요청 정보를 Handler Mapping이나 Controller에 전달
3. 처리된 결과 값을 ModelAndView(Model형태)로 반환
4. 사용자에게 보여주고자하는 page 포맷에 따라 ViewResolver가 page를 생성
5. 페이지 값에 model을 포함시켜서 전달

- DispatcherServletAutoConfiguration  
  : DispatcherServlet을 관리
- HttpMessageConverterAutoConfiguration  
  : 사용자의 요청을 처리한 후 사용자(클라이언트)에게 결과값을 반환시켜준다.
  json 포맷으로 데이터를 변환시킨 후 클라이언트에 반환한다.

**RestController**  
: @Controller + @ResponseBody
- spring mvc의 app에서는 controller를 생성하기 위해서 controller class를 spring 설정 파일에 등록해서 사용했었음. spring 4부터 annotation(@RestController)을 사용해서 등록할 수 있도록 지원한다.
- 별도의 spring xml 설정파일을 만들지 않고 annotation을 통해 각종 service, repository, controller와 같은 component들을 등록해서 사용할 수 있다.
- View(사용자에게 보여지는 page)를 갖지 않는 REST Data(JSON/XML)를 반환하는 controller
- 전달하고자하는 message값이 있을 경우 그 값을 response body에 포함하지 않고 전달할 수 있다.

**@Controller vs @RestController**
|방식|@Controller|@RestController|
|---|---|---|
|controller 생성|controller class를 spring 설정 파일에 등록해서 사용|annotation(@RestController)을 사용해서 등록|
|HTTP Response Body 생성|view기술을 사용|객체를 반환하면 JSON/XML 형식의 HTTP 응답을 직접 작성|
|주용도|view를 return|data를 return|

[Spring 4.x MVC Restful Web Service Work Flow]  
![image](https://user-images.githubusercontent.com/59961690/114715742-5bf9e800-9d6e-11eb-8099-309790090666.png)  

참고)  
@Controller VS, @RestController, ResponseEntity  
 : https://doublesprogramming.tistory.com/105  

### 1.8. Path Variable 사용

**Path Variable**  
ex) http://localhost:8080/books/ -> http://localhost:8080/books/1 or http://localhost:8080/books/123  
동일한 패턴을 가지고 있는데, 1, 123에 의해 URI가 달라졌다. 이는 별도의 분리된 URI를 만드는 것이 아니라 하나의 가변 변수를 가지고 있는 URI를 만들고, 이를 클라이언트가 변경해 호출하며 사용한다.  

## 2. User Service API 구현

### 2.1. User 도메인 클래스 생성

### 2.2. 사용자 목록 조회를 위한 API 구현 - GET HTTP Method

**Bean**  
: spring에서 선언되어 관리되는 isntance  
용도에 따라 controller bean, service bean, repository bean 등으로 사용된다.

**DI(Dependency Injection)**  
- 선언된 Bean을 관리하는 방법
- 개발자가 직접 서비스의 instance를 생성해서 사용하는 것이 아니라, spring framework에 의해 관리되도록 instance를 선언하고 사용한다.
- 방법 : xml, setter, 생성자, ...
- bean들을 사용하기 위해서는 container에 등록된 bean의 참조값을 받아와서 사용한다.
- spring container 혹은 IoC container에 등록된 bean들은 개발자가 프로그램 실행 도중에 변경할 수 없기 때문에 일관성있는 instance로 사용할 수 있다.

## 3. RESTful Service 기능 확장
### 3.1. 유효성 체크를 위한 Validation API 사용

**validation**
- @Size(min=<num>) : num size 이상의 데이터만 쓸 수 있다.
- @Past : 미래 데이터를 쓸 수 없고 과거 데이터만 쓸 수 있다. 

> **※ validation 라이브러리 사용 주의할 점**  
>
> spring boot version 2.3.0부터 web에 dependency로 spring-boot-starter-validation이 제외되었다.  
> 따라서 별도로 pom.xml에 추가해주어야 한다.  
> 참고) [백기선님 유튜브 강의](https://www.youtube.com/watch?v=cP8TwMV4LjE)

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

잘못된 데이터를 입력해서 유효성 검사에 걸릴 경우, 400 Bad request error 발생

### 3.2. 다국어 처리를 위한 Internationalization 구현
프로젝트 전반에 걸쳐 다국어 처리를 해야 하므로, 다국어 처리에 필요한 bean을 SpringBootApplication class에 등록해서 스프링부트가 초기화될 때 메모리에 등록되도록한다.  

`application.yml`에 다음 코드 추가  
`basename: messages` : 우리가 사용할 다국어 파일의 이름을 messages로 한다.
```yml
spring:
  messages:
    basename: messages
```
`resources/`하위에 다국어 파일 생성  
![image](https://user-images.githubusercontent.com/59961690/117149355-1c18a480-adf2-11eb-8a41-014a4d2a7e30.png)  
* messages.properties : default로 설정되는 다국어 파일
* messages_oo.properties : request Header에서 Accept-Language 의 value를 oo으로 보내면 해당 파일의 다국어가 리턴된다. (oo은 나라별 약칭)

**다국어 처리 참고 링크**  
[[SpringBoot] i18n 다국어 처리하기 ( MessageSource )](https://victorydntmd.tistory.com/340)

### 3.3. Response 데이터 형식 변환 - XML format
지금까지 우리는 client의 요청에 대해 json 포맷으로 response를 전송했다.  
client가 요청하는 api에 대해 xml 타입으로 전달하자.  
client가 GET 요청 시 header에 다음과 같이 요청을 보내면 json 형식이 아닌 xml 형식으로 response를 전송한다.  
![image](https://user-images.githubusercontent.com/59961690/117451329-5bbfc780-af7d-11eb-88ba-a01c775eb054.png)  

maven -> `pom.xml` 에,  
gradle -> `build.gradle` 에 다음 라이브러리를 추가하면, 위와 같이 xml 형식으로 요청시 xml 형식으로 응답한다.  
```xml
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
    <version>2.10.2</version>
</dependency>
```

### 3.4. Response 데이터 제어를 위한 Filtering

client가 user정보를 요청했을 때, 비밀번호나 주민등록번호처럼 중요한 데이터 값을 노출시키지 않기 위한 방법?
1. password와 ssn 필드에서 중요한 값을 다른 문자로 masking 처리해서 전송한다.
2. password와 ssn 필드를 null로 바꾸어 전송한다.
-> 여전히 password, ssn 필드는 전송된다는 문제점이 있다.  

***필드에 data값을 남기지 않고 보내는 방법은 없을까?***  
-> jackson 라이브러리의 @JsonIgnore 혹은 @JsonIgnoreProperties annotation을 추가한다.  

**@JsonIgnore**  
client에게 숨길 데이터에 달면, client가 요청 시 해당 데이터 필드는 무시되고 전송된다.  
```java
@JsonIgnore
private String password;
```

**@JsonIgnoreProperties**  
client에게 숨길 데이터의 class에 달면, client가 요청 시 설정한 데이터 필드는 무시되고 전송된다.  
```java
@JsonIgnoreProperties(value={"password", "ssn"})
public class User {
  ...
}
```

### 3.5. 프로그래밍으로 제어하는 Filtering 방법 - 개별 사용자 조회

**@JsonFilter(<filter 명>)**  
: filter ID를 문자열로 지정한다. 일단 이 annotation을 사용하면 무조건 FilterProvider와 해당 ID를 처리하는 필터를 제공해야 한다.  
 
**SimpleBeanPropertyFilter**  
: properties를 제어한다. 필드를 가지고 있는 클래스에 제어하고 싶은 객체가 있을 경우 filter 클래스를 사용하면 쉽게 제어할 수 있다.  

**SimpleBeanPropertyFilter.filterOutAllExcept(<필드 명>, ...)**  
: 지정된 필드들만 JSON 변환한다. 알 수 없는 필드는 무시한다. 이 방식을 권장한다. 명백히 검증된 필드만 내보낸다.  

**addFilter(<filter를 적용할 bean의 이름>, <filter>)**  

**setFilters(filters)**  
: filter를 적용한다.

**MappingJacksonValue(java.lang.Object value)**  
: 주어진 POJO를 연속적으로 wrapping할 새로운 instance를 생성한다.  

**POJO(Plain Old Java Object)**  
: 오래된 방식의 간단한 자바 오브젝트라는 말로서 Java EE 등의 중량 프레임워크들을 사용하게 되면서 해당 프레임워크에 종속된 "무거운" 객체를 만들게 된 것에 반발해서 사용되게 된 용어.  
본래 자바의 장점을 살리는 '오래된' 방식의 '순수한' 자바객체를 의미한다.  

### 3.6. 프로그래밍으로 제어하는 Filtering 방법 - 전체 사용자 조회

### 3.7. URI를 이용한 REST API Version 관리
카카오, 페이스북 developer 등에서 제공하는 API를 살펴보면 v1이 URI에 들어가있다. 이는 version을 URI에 명시한 것.  
우리도 그와 같이 URI에 version을 명시하도록 한다.  

**URI**
* ex) `@GetMapping("/v1/user/{id}")`
* URI에 version을 명시한다.
* request : http://localhost:8088/admin/v{version}/users/{id}

### 3.8. Request Parameter와 Header를 이용한 API Version 관리

**Request Parameter**
* ex) `@GetMapping(value = "/user/{id}/", params = "version=1")`
* 사용하려는 URI값 뒤에 request parameter값을 전달함으로써 version을 명시해준다. 
* `/user/{id}/` : 뒤에 version 정보가 전달되어야 하기 때문에 마지막에 '/'로 끝난다.
* request : http://localhost:8088/admin/users/{id}/?version={version}

**Header**
* ex) `@GetMapping(value = "/user/{id}", headers = "X-API-VERSION=1")`
* Header 값을 이용한 version 관리한다.
* request : http://localhost:8088/admin/users/{id}
* header : X-API-VERSION={version}

+) @GetMapping에 두가지 이상 정보가 올 때는 path를 'value = ' 으로 명시해준다.  

**MIME(Multi-purpose Internet Mail Extension) type**  
* ex) `@GetMapping(value = "/user/{id}/", produces = "application/vnd.company.appv1+json")`
* 이메일과 함께 전송되는 메일을 텍스트 문자로 전환해서 이메일 서버에 전달하기 위한 방법
* 최근에는 웹을 통해서 여러가지 파일을 전달하기 위해서 사용되는 일종의 파일 지정 형식
* request : http://localhost:8088/admin/users/{id}
* header : Accept=application/vnd.company.appv{version}+json

**Version 관리 기능**
* 단순하게 사용자에게 보여줄 항목을 제한하는용도가 아닌, rest API의 설계가 변경되거나 application의 구조가 바뀔 때도 version을 변경해서 사용해야 한다. 
* 사용자에는 어떤 API를 사용해야하는지 적절히 가이드를 명시해주어야한다. 
* URI, Request Parameter : URI 요청 정보를 변경하거나 뒤에 parameter를 변경하면 되므로, 일반 브라우저에서도 실행이 가능하다.
* header, MIME type : 일반 인터넷 웹 브라우저에서는 실행할 수 없다. 별도의 프로그램을 이용해야한다.

**Version 관리에서 중요한 점**
* URI에 너무 과도하게 정보를 표기하는 것은 지양한다.
* 잘못된 Header값을 사용하지 않도록 한다.
* 인터넷 웹 브라우저의 캐시 기능으로 인해서 지정한 값이 제대로 반영되지 않을 수 있다.
* API의 적절한 용도에 따라서 웹 브라우저에서 실행될 수 있어야 한다.
* 개발하고 제공하는 rest API에 대해서 적절한 개발 도움 문서가 제공되어야 한다.

## 4. Spring Boot API 사용

### 4.1. Level3 단계의 REST API 구현을 위한 HATEOAS 적용
**HATEOAS(Hypermedia As the Engine Of Application State)**  
: 현재 리소스와 연관된(호출 가능한) 자원 상태 정보를 제공  

REST 아키텍처 레벨  
![image](https://user-images.githubusercontent.com/59961690/117565751-df98c180-b0ed-11eb-8a94-a1447d8f4633.png)  
* Level 0 : The Swamp of POX (원격 프로시저 호출)
  > 일반 XML 데이터를 SOAP이나 XML-RPC 등으로 전송한다. POST 메소드만 사용하며, 서비스간에 단일 POST 메소드로 XML 데이터를 교환한다. 초창기 SOA 애플리케이션 제작 시 흔히 사용된 방식이다.
  * REST 방식으로 어플리케이션이 고려되었다기 보다는, 기존의 리소스를 웹서비스 형태로 제공해서 단순히 URI만 mapping한 형태
  * 굳이 동작값, 상태값을 URI에 표시한다. -> 불필요하다.
  > ex)  
  > http://server/getPosts  
	> http://server/deletePosts  
	> http://server/doThis  
* Level 1 : Resources (Rest 리소스)
  > 함수에 파라미터를 넘기는 대신 REST URI를 이용한다. 레벨 0 처럼 POST 메소드 하나밖에 사용하지 않지만, POST 메소드로 서비스간 통신을 하면서 복잡한 기능을 여러 리소스로 분산시킨다는 점에서 한 단계 발전된 형태라고 볼 수 있다.
  * 일정한 패턴을 가지고 작성되었지만, HTTP의 메소드 별로 서비스를 구분해서 사용하지는 않는다.
  * 즉, 리소스 형태와 작업의 종류에 맞춰 적적한 HTTP 메소드를 지정하고 있지 않다. 
  * 사용자 요청을 단순히 GET, POST만으로 처리하고, 모든 반환 값을 error 혹은 200 OK라는 status code로 반환하는 단순한 형태
  * URI는 적절하게 구분되어있는 것 같지만, 아직까지도 HTTP 메소드에 맞춰서 상태값을 요청하거나 처리하지 않는 단계
  > ex)
  > http://server/accounts
  > http://server/accounts/10
* Level 2 : HTTP Verbs (추가 HTTP 메소드)
  > 레벨2는 POST 이외에 GET, HEAD, DELETE, PUT 메소드를 추가적으로 사용한다. HTTP 요청시 여러 메소드를 사용하여 다양한 리소스를 제공할 수 있다는 점에서 REST의 진정한 유스 케이스라 할 수 있다.
  * Level 1 + HTTP 메소드
  * 우리가 제공하려는 리소스를 용도와 상태에 맞춰서 적절하게 HTTP 메소드를 설계하고 서비스한다.
  * 리소스가 변경할 수 없는 단순한 읽기 전용의 데이터일 경우 HTTP GET 메소드를 사용하고, 새로운 리소스를 추가할 때는 POST, 리소스의 상태를 변경할 때는 PUT, 리소스를 삭제하고자할 때는 DELETE 메소드를 사용해서 서비스한다.
  * 데이터를 조작하는 CRUD와 같은 개념을 HTTP 메소드인 GET, POST, PUT, DELETE에 매칭해서 서비스한다.
  * HTTP 메소드를 이용해서 리소스 상태를 구분해서 서비스하면, 비슷하거나 같은 URI라 하더라도 메소드를 달리해서 다른 서비스를 제공할 수 있다.
* Level 3 : Hypermedia Controls (HATEOAS)
  > 애플리케이션 상태 엔진으로서의 하이퍼미디어는 리차드슨 성숙도 모델의 가장 성숙한 단계로서, 요청에 대한 하이퍼미디어 응답 속에 클라이언트가 다음에 취해야 할 액션에 관한 상태 정보가 담겨 있다. 레벨 3은 발견 가능성(Discoverability)이 높고, 응답 자체에 대한 필요한 내용이 고스란히 담겨 있다. 리소스 뿐만 아니라 그 이상의 부가적인 정보까지 나타낸다는 점에서 HATEOAS가 진정한 REST냐 하는 문제는 아직도 논란의 여지가 있다.
  * Level 2 + HATEOAS
  * 다음 작업으로 어떤 것을 할 수 있는지, 그 작업을 하기 위해서 다뤄져야하는 URI에 어떤 action이 있는지 알려준다.
  * Client측이 server가 제공하는 서비스를 일일히 찾아보지 않아도 된다.
  * end point만 가지고도, 서버의 다음 URI값을 알 수 있다.

참고)  
[[Spring] Level3 REST API 구현을 위한 HATEOAS 적용](https://transferhwang.tistory.com/125)  
[Spring Boot를 이용한 RESTful Web Services 개발](https://www.inflearn.com/course/spring-boot-restful-web-services/dashboard) 강의의 RESTful API 설계 가이드 - Richardson Maturity Model 소개  

`pom.xml`에 hateoas dependency 추가  
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>
<!-- 강의에서는 추가하지 않았는데 나는 아래 dependency 추가해야 에러가 안남-->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-hateoas</artifactId>
</dependency>
```

기능|spring 2.1.8 version 이하|spring 2.2 version 이상|
|---|---|---|
|추가 자원|Resource|EntityModel|
|추가 자원 link|ControllerLinkBuilder|WebMvcLinkBuilder|

**HATEOAS**  
* 하나의 리소스에서 파생할 수 있는 추가적인 작업을 확인할 수 있다.
* 현재 있는 리소스에서 추가로 어떠한 다른 작업을 할 수 있는지 link할 수 있다.
* 사용자의 입장에서 다양한 정보를 한번에 얻을 수 있다는 장점

### 4.2. REST API Documentation을 위한 Swagger 사용

**Swagger**
* 개발자 도움말 필드를 생성
* rest 웹 서비스에서 설계, 빌드, 문서화, 사용에 관련된 작업을 지원해주는 오픈소스 프레임워크
* 사용자, 개발자에 관련된 document 페이지 생성
* 아파치 2.0 라이브러리 사용
* 선언시켰던 클래스, 컨트롤러를 화면에 보기 좋게 표시해주는 역할을 한다. 

**강의 내용**
`pom.xml`에 dependency 추가  
```xml
<dependency>
  <groupId>io.springfox</groupId>
  <artifactId>springfox-swagger2</artifactId>
  <version>2.9.2</version>
</dependency>
<dependency>
  <groupId>io.springfox</groupId>
  <artifactId>springfox-swagger-ui</artifactId>
  <version>2.9.2</version>
</dependency>
```
* `springfox-swagger-ui` : 개발하고 있는 documentation을 시각화해주는 도구

**+) 가장 최신인 2.10.5 version을 사용하지 않는 이유 (2021-05-09 기준)**  
2.10 대 version은 3.0을 위한 중간단계라서 사용하지 않는 것을 추천.  
참고) [springfox issue page](https://github.com/springfox/springfox/issues/3336)  

***Spring boot 최신 version(2.4.4)의 경우 위의 방법에서 Swagger 오류가 발생한다.***  

**Solution**
`pom.xml`에 위 강의 내용의 dependency를 빼고, 아래의 dependency 추가  
```xml
<dependency>
  <groupId>io.springfox</groupId>
  <artifactId>springfox-boot-starter</artifactId>
  <version>3.0.0</version>
</dependency>
```
* 웹브라우저에서 확인할 수 있다. 
* http://localhost:8088/v2/api-docs : Swagger file에 의해서 만들어진 내용이 json 형식으로 보여진다.
* http://localhost:8088/swagger-ui/ : html 형식으로 보여진다.

**api-docs**  
![image](https://user-images.githubusercontent.com/59961690/117573821-f8689d80-b114-11eb-8755-584c5f3ccece.png)  
* info : swagger-ui page 상단의 api documentation, license 정보 등이 표시된다.
* host : 기본 IP, PORT
* path : Swagger를 사용하는 application이 제공하는 url값을 확인할 수 있다.
* definations : 현재 Swagger에서 사용하고 있는 다양한 instance들을 만들 때 사용된 객체들.

**swagger-ui**  
![image](https://user-images.githubusercontent.com/59961690/117573812-edae0880-b114-11eb-9c08-4ec9facc39f4.png)  

### 4.3. Swagger Documentation 구현 방법
Swagger Documentation을 customize한다.  

**변경 내용**
* Contact
* ApiInfo
* Produces and Consumes

**@ApiModel(description = <String>), @ApiModelProperty(notes = <String>)**  
: property, field 별로 documentation을 사용할 수 있는 설명을 기재한다.  
* api-docs, swagger ui 에서 모두 확인할 수 있다. 

### 4.4. REST API Monitoring을 위한 Actuator 설정

`pom.xml`에 dependency 추가  
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
![image](https://user-images.githubusercontent.com/59961690/117576925-97e05d00-b122-11eb-8f8d-7ad16e68ac95.png)  
* 접속 url : http://localhost:8088/actuator
* 쓸 수 있는 모니터링의 전체 목록을 확인 가능하다.
* 각 field 안의 href를 접속하면 해당 field에 대해 자세히 확인이 가능하다.

더많은 정보를 확인하기 위해 application.yml 파일에 몇가지 설정을 한다.  
`application.yml`에 다음 코드 추가  
```yml
menagement:
  endpoints:
    web:
      exposure:
        include: "*"
```  
![image](https://user-images.githubusercontent.com/59961690/117577201-c579d600-b123-11eb-9633-572fc6b7e7df.png)  
* 보다 다양한 정보를 확인할 수 있다.

### 4.5. HAL Browser를 이용한 HATEOAS 기능 구현

**HAL(Hypertext Application Language) Browser**
* REST API 설계시 response 정보에 부가적인 정보를 추가해서 제공하는 Service
* API resource들 사이에서 쉽게 일관적인 hyperlink를 제공하는 방식 
* API 설계시 HAL을 도입하게 되면 API 간에 쉽게 검색이 가능하다.
* 따라서 해당 API를 사용하는 다른 개발자들에게 좀 더 나은 개발환경 제공
* HAl을 API response message에 적용하게 되면 그 message가 json 형식이건, xml 형식이건 API를 쉽게 사용할 수 있는 부가적인 정보(메타 정보)를 hyperlink 형식으로 간단하게 포함할 수 있다.

![image](https://user-images.githubusercontent.com/59961690/117577565-438aac80-b125-11eb-86f5-d67be7841529.png)  
* 우리가 제공하려는 특정한 resource의 정보를 link로 추가해서 제공할 수 있다.
* resource를 외부에 공개하기 위해서 RESTful service를 사용해 왔던 것이다. 해당하는 요청 작업에 부가적으로 사용할 수 있는 또 다른 resource를 연결해서 보여주기 위해 html의 link, 즉 hypertext 기능을 사용한다.

`pom.xml`에 dependency 추가  
```xml
<dependency>
  <groupId>org.springframework.data</groupId>
  <artifactId>spring-data-rest-hal-browser</artifactId>
  <version>3.3.2.RELEASE</version> <!-- spring boot 2.4.x의 경우 hal의 version도 명시해주어야 한다. -->
</dependency>
```

![image](https://user-images.githubusercontent.com/59961690/117579023-1097e700-b12c-11eb-9258-3f65588c088c.png)  
* 접속 url : http://localhost:8088/browser/index.html
* Explorer : actuator, actuator metrics, actuator metrics memory 등을 입력해서 쉽게 부가적인 정보를 확인할 수 있다.
* 이전에 HATEOAS 기능을 이용해서 resource를 요청할 때 link 사용이 가능한 resource를 연결했다. 
* Hal은 이러한 link정보를 json으로 표현할 수 있는 표준
  > example
  >> http://localhost:8088/browser/index.html#/actuator/
  >> http://localhost:8088/browser/index.html#/actuator/metrics/
  >> http://localhost:8088/browser/index.html#/actuator/metrics/jvm.memory.max

**Hal browser의 장점**  
* rest 자원을 표시하기 위한 자료구조를 그 때 그 때 생성하지 않더라도 Hateoas 기능을 사용할 수 있다.
  * HATEOAS를 사용했을 때는 HATEOAS 정보를 추가하기 위해 link 객체를 생성했다.
    ```java
    // UserController.java
    // HATEOAS
    EntityModel<User> model = new EntityModel<>(user);
    // 현재 클래스가 가지고 있는 retrieveAllUsers(전체 사용자 목록 보기) 메소드를
    WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
    // all-users와 link 한다.
    model.add(linkTo.withRel("all-users"));
    ```
  * 반면 Hal은 필요한 resource를 직접 그 때 그 때 작업하지 않더라도 추가로 사용할 수 있는 link가 자동으로 설계된다.

### 4.6. Spring Security를 이용한 인증 처리
지금까지 REST API는 웹브라우저 혹은 postman과 같은 client test program을 이용해서 결과를 확인했다.  
일반적으로 공개되어도 좋은 정보가 아닌, 중요한 리소스의 경우 보안에 문제가 생길 수 밖에 없는 구조였다.  

**REST API application이 인증을 처리하기 위한 방법**
1. 토큰을 이용한 방식 
2. ID와 비밀번호를 이용한 방식

**Spring security**  
`pom.xml`에 dependency 추가  
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

![Screenshot from 2021-05-11 20-00-40](https://user-images.githubusercontent.com/59961690/117825957-b709f680-b2aa-11eb-82da-2d31ce5edbda.png)  
* log 파일을 함께 확인한다.
* spring security library를 사용하면 현재 사용하고 있는 프로젝트에서 사용할 수 있는 password를 자동으로 생성해주기 때문이다.
* 웹서비스를 호출하기 위해서 이 password가 필요하다.

![Screenshot from 2021-05-11 20-02-39](https://user-images.githubusercontent.com/59961690/117825963-b83b2380-b2aa-11eb-9d5d-a227e43107d7.png)  
* 인증이 되지 않은 상태에서 평소처럼 resource를 요구하면 에러가 발생한다. 

![Screenshot from 2021-05-11 20-04-48](https://user-images.githubusercontent.com/59961690/117825969-b96c5080-b2aa-11eb-97ac-c8d07f9e1d57.png)  
* URI 값에 인증 정보를 같이 전달하면 보안상 큰 문제가 발생하기 때문에 일반적으로 URI값에는 인증 정보를 포함하지 않는다.
* Header값에 인증 정보를 포함할 수 있다.

### 4.7. Configuration 클래스를 이용한 사용자 인증 처리
Spring security에 의해서 자동으로 생성된 password가 아닌, 개발자가 직접 지정한 ID와 password를 사용해서 인증 처리한다.

**`application.yml`에 지정**  
`application.yml`에 다음 코드 추가  
```yml
spring:
  security:
    user:
      name: username
      password: passw0rd
```  
* 원하는 id와 password를 기입한다.

결과  
![Screenshot from 2021-05-11 20-57-54](https://user-images.githubusercontent.com/59961690/117825977-bbceaa80-b2aa-11eb-8949-d953035338e4.png)
* id와 password를 application.yml에 고정적으로 지정하게 되면 id나 password를 변경할 때마다 서버를 재기동해야한다.
* 따라서 고정된 id를 사용하는 방식이 아닌 database 등에서 사용자 정보를 가져오는 방식으로 프로그램을 수정해야한다.

**Configuration**  
WebSecurityConfigurerAdapter를 상속받아 로그인 처리를 구현한다.  
* spring-cloud-bus 기능을 이용하게 되면 서버의 재부팅 없이 업데이트가 가능하다.

결과
![Screenshot from 2021-05-11 21-26-30](https://user-images.githubusercontent.com/59961690/117825980-bc674100-b2aa-11eb-9dc1-d42c641248a9.png)

## 5. Java Persistence API 사용

### 5.1. Java Persistence API의 개요

**JAP(Java Persistence API)**  
- 자바 ORM 기술에 대한 API 표준 명세
- 자바 어플리케이션에서 관계형 데이터베이스를 사용하는 방식을 정의한 인터페이스
  - JPA는 말 그대로 '인터페이스'이다.
  - 인터페이스는 특정한 메소드의 구현체가 존재하지 않는 규약이다.
  - 우리가 특정 기능을 사용할 수 있는 라이브러리가 아닌, 자바 어플리케이션에서 관계형 데이터 베이스를 어떻게 사용하면 될 지 정의해 놓은 인터페이스이자 약속이다.
  - JPA는 단순히 명세서이기 때문에 구현된 메소드가 없고, 선언만 존재한다.
- EntityManager를 통해 CRUD 처리

**Hibernate**  
- JPA를 구현한 대표적인 구현체
- 객체 관계 mapping(ORM) framework
- JPA의 구현체, 인터페이스를 직접 구현한 라이브러리
- 직관적이며, 비즈니스 로직에 집중할 수 있다.
- ORM을 위한 라이브러리임과 동시에, EntityManager를 구현 시킨 클래스 집합체

**ORM(Object Relationship Mapping)**  
- 재사용성이 좋다.
- 복잡한 데이터베이스의 쿼리를 모른다고 하더라도 자바의 객체와 데이터베이스의 entity가 자동으로 mapping되고 있기 때문에 간단한 데이터베이스 어플리케이션 개발에 많이 사용된다.

**Spring Data JPA**
- Spring Module
- 개발자가 JPA를 좀 더 쉽고 편하게 사용할 수 있게 도와준다.
- JPA를 추상화한 Repository 인터페이스 제공

### 5.2. JPA를 사용을 위한 Dependency 추가와 설정

`pom.xml`에 dependency 추가  
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
  <groupId>com.h2database</groupId>
  <artifactId>h2</artifactId>
  <scope>runtime</scope>
</dependency>
```

`application.yml`에 다음 코드 추가  
```yml
spring:
#  security:
#    user:
#      name: username
#      password: passw0rd
      
  jpa:
    show-sql: true
  
  h2:
    console:
      enabled: true
```
* sql에 관련된 log 파일을 출력
  * 개발의 편의성을 위해 어떤 쿼리가 실행되었는지 log 파일에 출력시킨다.
* h2 console이라는 application을 실행한다. 

![Screenshot from 2021-05-12 00-24-45](https://user-images.githubusercontent.com/59961690/117844764-ea548180-b2ba-11eb-8ca0-114528f4525d.png)  
* http://localhost:8088/h2-console
* spring security, config에 의해서 맨 처음에는 로그인 화면이 출력된다.
  * 앞서 설정한 ID와 password를 입력하면 접근이 가능하다.
  * 하지만 접근 후에도 `Test Connection` 클릭시 에러 페이지가 출력되므로 security config 설정을 바꾸도록 한다.
    * `SecurityConfig` 클래스에 configure(HttpSecurity http)를 override한다.
    * /h2-console/** 에 대한 http 요청을 모두 통과한다 -> permitAll()
* JDBC URL을 jdbc:h2:mem:testdb 로 변경한다.
* `Test Connection`을 누른다.
  * 이 때, database "mem:testdb"를 찾을 수 없다는 오류가 발생한다면 다음과 같이 해결한다.
    * 스프링부트에서는 내부적으로 아래와 같은 정보를 자동으로 생성한다.
      > Driver Class : org.h2.Driver  
      > JDBC URL : jdbc:h2:mem:testdb  
      > Username : sa  
      > Password :  
    * 그러나 최신 h2 버전에서는 데이터베이스를 미리 생성하는 것을 방지하도록 설정되어있다.
    * 따라서 h2 버전을 1.4.197 이하를 사용하거나, `application.yml`에 다음 코드를 추가한다.
      ```yml
      spring:
        datasource:
          url: jdbc:h2:mem:testdb
        jpa:
          show-sql: true
        h2:
          console:
            enabled: true
      ```

![Screenshot from 2021-05-12 00-25-41](https://user-images.githubusercontent.com/59961690/117844769-ec1e4500-b2ba-11eb-9f88-bc59ff250d1f.png)  
* `Connect`를 클릭한다.

### 5.3. Spring Data JPA를 이용한 Entity 설정과 초기 데이터 생성
user 도메인 클래스와 Entity를 연동한다.  

**@Entity**  
: 해당하는 클래스 이름을 가지고 데이터베이스에 테이블을 생성하고, 클래스에 선언되어있는 필드의 정보를 가지고 테이블 생성에 필요한 컬럼 정보로 사용한다.  

**@Id**  
: 기본키 값을 설정한다.  

**@GeneratedValue**  
: 자동 생성되는 키 값 

**위와 같이 Entity, Id, GeneratedValue annotation을 사용하는 이유**
* table을 생성하기 위해 create table user {...} 와 같은 명령어를 실행할 필요가 없다.
* 자바 object 선언만 시켜서 데이터베이스에 해당 객체와 mapping되는 작업을 진행할 수 있다.
* table에 저장되어있는 데이터 값도 자바의 object형태로 가져올 수 있다.

**insert data**
* `resources/`에 확장자명이 `.sql`인 파일을 생성한다.
* `resources/`안의 `.sql` 파일을 스프링 부트가 처음 로딩할 때 초기 데이터로 생성한다.
* 해당 파일에서 아래 예시와 같은 sql 명령어를 통해 데이터를 삽입하면 서버 구동 시 data가 삽입된다.
  ```sql
  insert into user values(1, sysdate(), 'User1', 'test1111', '701010-1111111');
  ```

### 5.4. JPA Service 구현을 위한 Controller, Repository 생성
Spring Data JPA는 일반적인 JPA 기능과 달리 entity를 제어하기 위해서 JPA EntityManager를 사용하지 않고, repository라는 interface를 선언하게 되어있다.  
단순히 repository interface를 선언하여 CRUD에 관련된 메소드를 사용할 수 있다.  

**@Repository**  
: bean의 타입. 데이터베이스에 관련된 형태의 bean

**JpaRepository<T,ID> 상속**  
* T : 현재 지정한 클래스가 어떠한 type의 객체를 다룰 것인지 명시한다.
* ID : T의 기본키의 type을 명시한다.
* 특별하게 데이터베이스에 관련된 작업(ex. select)없이 결과값을 볼 수 있다.

### 5.5. JPA를 이용한 사용자 목록 조회 - GET HTTP Method

**PagingAndSortingRepository<T, ID>**  
* JpaRepository<T,ID>가 상속하고 있는 interface이다.
* findAll에 관련된 메소드가 선언되어있다.

**CrudRepository<T, ID>**  
* PagingAndSortingRepository<T, ID>가 상속하고 있는 interface이다.
* findById(ID var1) 메소드가 선언되어 있다.

**`UserJpaController.java`**
```java
@GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        // 데이터가 존재하지 않을 경우도 있으므로 Optional 타입으로 데이터를 받는다.
        Optional<User> user = userRepository.findById(id);

        // user 안에 데이터가 존재하지 않는다면
        if (!user.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // HATEOAS
        // 반환시켜주고자하는 user 객체에 retrieveAllUsers(전체 user 보기) 링크를 추가한다.
        EntityModel<User> resource = new EntityModel<>(user.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }
```
* Optinal<T> findById(ID var1)  
  : return type이 Optional 데이터이다.
  * Optional 데이터인 이유 : 데이터가 검색어에 따라서 존재할 수도, 존재하지 않을 수도 있기 때문에 선택적인 데이터 값을 반환한다.
* isPresent()  
  : user 안에 데이터가 존재하는가
* get()  
  : 데이터 값이 실제로 존재할 경우에만 데이터 값을 반환한다.
* HATEOAS  
  : 전체 user 보기 주소 값을 link한다.
  * HATEOAS 작업을 하기 위해서 해당하는 주소 값을 직접 명시하지 않고, 그 주소 값에 해당하는 메소드의 이름만으로 link 연결이 된다.

### 5.6. JPA를 이용한 사용자 추가와 삭제 - POST/DELETE HTTP Method  

### 5.7. 게시물 관리를 위한 Post Entity 추가와 초기 데이터 생성  
한 명의 사용자는 여러 개의 게시물을 작성할 수 있다.  

`Post.java`  
```java
  @ManyToOne(fetch = FetchType.LAZY
  @JsonIgnore
  private User user
```
* @ManyToOne  
  : 데이터가 여러 개 올 수 있고, 하나의 값과 매칭될 수 있다.  
* LAZY  
  : 지연 로딩 방식. 사용자 entity를 조회할 때 매번 post entity가 같이 로딩되는 것이 아닌, post 데이터가 로드되는 시점에 필요한 사용자 데이터를 가지고 온다.
  
`User.java`
```java
  @OneToMany(mappedBy = "user")
  private List<Post> posts;
```
* @OneToMany  
  : 데이터를 1:N 으로 매칭 할 수 있다.
  
**@ManyToOne, @OneToMany**  
: 관계형 데이터 베이스에서 말하는 관계를 이야기 한다. 관계가 형성되는 차수를 이야기한다.
	
### 5.8. 게시물 조회를 위한 Post Entity와 User Entity와의 관계 설정
	
> **※ 무한순환참조현상 해결 방법 ※**  
> 
> * Users에서 Posts의 정보를 가져와서 JSON으로 변환하고, Posts에서는 Posts를 작성한 Users의 정보를 다시 JSON으로 변환하는 무한 반복이 생기는 현상.
> * Users와 Posts 사이에서 양방향 JSON 변환이 반복되면서 무한히 서로 참조하여, 비정상적으로 무한한 데이터값이 response로 오게 된다.
> 
> 1. @OneToMany를 사용하는 entity(Users)에 @JsonManagedReference annotation을 붙인다.
> 2. @ManyToOne을 사용하는 entity(Posts)에 @JsonBackReference annotation을 붙인다.

참고)  
[20190720 [문제해결] Infinite recursion (StackOverflowError)](https://pasudo123.tistory.com/350)

### 5.9. JPA를 이용한 새 게시물 추가 - POST HTTP Method

## 6. RESTful API 설계 가이드

### 6.1. Richardson Maturity Model 소개
  
**Leonard Richardson의 Maturity Model**  
* REST API를 개발할 때 확인해야 할 REST 방식의 주요 요소들을 3개 단계로 나눈 모델
* Resorces, http Method, Hypermedia 등의 개념을 가지고 리소스를 표현한다.
* 자세한 내용은 다음 내용 참고  
  [Level3 단계의 REST API 구현을 위한 HATEOAS 적용](#41-level3-단계의-rest-api-구현을-위한-hateoas-적용)

### 6.2. REST API 설계 시 고려해야 할 사항

* Consumer first  
  개발자 중심의 설계보다는 해당 API를 사용하는 소비자의 입장에서 간단 명료하고 직관적인 API로 설계되어야한다.
* Make best use of HTTP  
  HTTP 메소드와 Request, Response Header값과 같이 HTTP의 장점을 최대한 살려 설계해야 한다.
* Request methods  
  최소한 Maturity Model의 Level 2 Model 가지는 특징을 가지고 각 리소스 별로 적절한 HTTP 메소드를 제공해야한다.
  * GET : 조회, 보기, 읽기 전용
  * POST : 데이터 추가
  * PUT : 리소스 변경
  * DELETE : 리소스 삭제
* Response Status  
  적절한 상태코드를 결정해서 반환해야한다.
  * 200
  * 404
  * 400
  * 201
  * 401
* No secure info in URI
* User plurals  
  단수 형태의 URI가 아닌 복수 형태의 URI로 사용하고, 특정한 리소스를 지정하고 싶을 때는 다음 depth의 URL로 표현하는 것이 좋다.
  * prefer `/users` to `/user`
  * prefer `/users/1` to `/user/1`
* User nouns for resources  
  리소스에 대한 정보는 동사 형태보다는 명사 형태로 표시하는 것이 좋다.  
  사용자 입장에서 더 직관적이고, 간단 명료하게 어떤 서비스, 리소스를 제공하는 API인지 명시할 수 있다.
* For exceptions  
  일관된 접근 end point를 사용하는 것이 좋다. 
  * define a consistent approach
    > /search
    > PUT /gists/{id}/star
    > DELETE /gists/{id}/star
