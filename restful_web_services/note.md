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

## 2.1. User 도메인 클래스 생성

## 2.2. 사용자 목록 조회를 위한 API 구현 - GET HTTP Method

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
## 4. Spring Boot API 사용
## 5. Java Persistence API 사용
## 6. RESTful API 설계 가이드
