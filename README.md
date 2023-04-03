# 스프링 부트 쇼핑몰 프로젝트 with JPA 학습 Repository

## 1장 개발 환경 구축

### 1.1 스프링 부트의 특징

### 1.2 JDK 설치

### 1.3 인텔리제이 설치

### 1.4 애플리케이션 실행하기

1.4.1 Spring Boot Project 생성하기
  
1.4.2 빌드 도구

1.4.3 설정 파일 (application.propertes)
  
1.4.4 Hello World 출력하기
  
### 1.5 Lombok 라이브러리
 
### 1.6 MySQL 설치하기

## 2장 Spring Data JPA

### 2.1 JPA

2.1.1 JPA란?

* 객체지향과 관계형 데이터베이스 간의 패러다임이 불일치하기 때문에 해결하기 위해서 나온 기술이 ORM
* JPA는 ORM 기술의 표준 명세로 자바에서 제공하는 API, 즉 JPA는 인터페이스고 이를 구현한 대표적인 구현체로는 Hibernate, EclipseLink, DataNucleus, OpenJpa, TopLink

2.1.2 JPA 동작 방식

* 엔티티
    
    데이터베이스이 테이블에 대응하는 클래스

* 엔티티 매니저 팩토리

    엔티티 매니저 인스턴스를 관리하는 주체이며 애플리케이션 실행 시 한 개만 만들어지며 사용자로부터 요청이 오면 엔티티 매니저 팩토리로부터 엔티티 매니저를 생성
    
* 엔티티 매니저

    영속성 컨텍스에 접근하여 엔티티에 대한 데이터베이스 작업을 제공하며 내부적으로 데이터베이스 커넥션을 사용해서 데이터베이스 접근
    find, persist, remove, flush 메소드

* 영속성 컨텍스트

    엔티티를 영구 저장하는 환경으로 엔티티 매니저를 통해 영속성 컨텍스트에 접근
    ![image](https://user-images.githubusercontent.com/126080146/226778294-345a818e-7398-4d41-ad36-ac18cf129f79.png)
    ![image](https://user-images.githubusercontent.com/126080146/226778325-cbd3446c-9d98-4794-a42e-0a34c9393f67.png)

    [영속성 컨텍스트 사용 시 이점]

    * 1차 캐시

        영속성 컨텍스트에는 1차 캐시가 존재하며 Map<KEY, VALUE>로 저장
        
    * 동일성 보장

        하나의 트랜잭션에서 같은 키값으로 영속성 컨텍스트에 저장된 엔티티 조회 시 같은 엔티티 조회를 보장 (1차 캐시에 저장된 엔티티를 조회)
        
    * 트랜잭션을 지원하는 쓰기 지연

        persist() 호출하면 1차 캐시에 저장되는 것과 동시에 쓰기 지연SQL 저장소에 SQL문이 저장, flush 되면서 데이터베이스에 반영 (성능 이점)
        
    * 변경 감지

        1차 캐시에 저장된 엔티티와 스냅샷을 비교 후 변경 내용이 있다면 UPDATE SQL문을 쓰기 지연SQL 저장소에 담아두고 커밋 시점에 변경 내용을 자동 반영 (Update문 호출 필요 없음)

### 2.2 쇼핑몰 프로젝트 생성하기

2.2.1 프로젝트 생성

    https://start.spring.io/ 접속하여 프로젝트 생성 (패키지 이름 com.shop)
    Lombok, Thymeleaf, Spring Data JPA, Spring Web, MySQL Driver, H2 Database 의존성 추가

2.2.2 application.properties 설정하기

    application.properties 주석 정리
    데이터베이스 초기화 전략 - DDL AUTH 옵션
    
### 2.3 상품 엔티티 설계하기

2.3.1 상품 엔티티 설계하기

    com.shop.entity 패키지 생성
    com.shop.entity.item.javs 생성
    com.shop.constant 패키지 생성 (enum 타입 모음)
    com.shop.constant.ItemSellStatus.java 생성

  ![image](https://user-images.githubusercontent.com/126080146/226781287-d979cbdb-782d-4c54-addf-6cc3f716d753.png)

  ![image](https://user-images.githubusercontent.com/126080146/226781405-1c085ffb-dfc2-493e-8ff0-8020c26642a1.png)

  ![image](https://user-images.githubusercontent.com/126080146/226781450-16a00173-cf84-406a-b75a-d1ccf64c88aa.png)

### 2.4 Repository 설계하기

    com.shop.repository 패키지 생성 후 ItemRepository 인터페이스 생성
    테스트 환경 시 h2 데이터베이스를 사용하도록 resources 아래에 application-test.properties 파일 생성
    ItemRepository 인터페이스에서 Test 생성 (ItemRepsotiroyTest)

### 2.5 쿼리 메소드

  ![image](https://user-images.githubusercontent.com/126080146/227088687-9f0394f6-d894-4988-b012-1fe49648bbd3.png)

    - 상품의 이름을 이용하여 데이터를 조회하는 예제
        ItemRepository 인터페이스에 findByItemNm 메소드 추가
        ItemRepositoryTest 에 Test 코드 작성
        
    - OR 조건 처리 예제
        ItemRepository 인터페이스에 findByItemNmOrItemDetail 메소드 추가
        ItemRepositoryTest 에 Test 코드 작성
    
    - LessThan 조건 처리 예제
        ItemRepository 인터페이스에 findByPriceLessThan 메소드 추가
        ItemRepositoryTest 에 Test 코드 작성
        
    - OrderBy 정렬 처리 예제
        ItemRepository 인터페이스에 findByPriceLessThanOrderByPriceDesc 메소드 추가
        ItemRepositoryTest 에 Test 코드 작성

### 2.6 Spring DATA JPA @Query 어노테이션

    - @Query 어노테이션을 이용하면 SQL과 유사한 JPQL 이라는 객체지향 쿼리 언어를 통해 복잡한 쿼리도 처리 가능
    - JPQL 은 엔티티 객체를 대상으로 쿼리를 수행하는 객체지향 쿼리
    - SQL 을 추상화해서 사용하기 때문에 특정 데이터베이스 SQL 에 의존하지 않아 데이터베이스가 변경되어도 애플리케이션 영향을 받지 않
    - 검색 예제
        ItemRepository 인터페이스 findByItemDetail 메소드 추가
        ItemRepositoryTest 에 Test 코드 작성
        
### 2.7 Spring DATA JPA Querydsl

    - 작성 쿼리 중 오타가 들어갈 경우 애플리케이션을 실행하기 전까지 오류를 인지하지 못하며, 로딩 시점에 파싱 후 에러를 잡도록 도음을 주는 것이 Querydsl
    - Querydsl 은 JPQL 을 코드로 작성할 수 있도록 도와주는 빌더 API
    
    [Querydsl 장점]
    
    * 고정된 SQL 문이 아닌 조건에 맞게 동적으로 쿼리 생성
    * 비슷한 쿼리를 재사용할 수 있으며 제약 조건 조립 및 가독성을 향상
    * 문자열이 아닌 자바 소스코드로 작성하기 때문에 컴파일 시점에 오류를 발견
    * IDE 의 도움을 받아서 자동 완성 기능을 이용할 수 있기 때문에 생산성 향상
    
    [기본셋팅]
    * 사용을 위해 querydsl-jpa, querydsl-apt 의존성 추가
    * Maven -> Reload All Maven Projects
    * maven compile 더블 클릭 > target/generated-sources 폴더에 QItem 클래스 생성
    
    - JPAQueryFactory 이용한 상품 조회 예제
      ItemRepositoryTest 에 Test 코드 작성

  ![image](https://user-images.githubusercontent.com/126080146/227441789-fc22fec3-793e-4a8b-a820-30d393f54c74.png)

      ItemRepository 인터페이스에 QueryDslPredicateExecutor 인터페이스 상속
      
  ![image](https://user-images.githubusercontent.com/126080146/227442854-4d0cd8fe-71dd-43d2-9d65-ffc9ac518071.png)

      
## 3장 Thymeleaf 학습하기

### 3.1 Thymeleaf 소개

    - 서버 사이트 템플릿 엔진으로는 Thymeleaf (스프링 권장), Freemarker, Groovy, Mustache 등이 존재
    - Thymeleaf 의 가장 큰 장점은 'natural templates'
    - Thymeleaf 의 확장자명은 html 이며, 문법은 html 태그 안쪽에 속성으로 사용
    
    com.shop 패키지 아래 controller 패키지 생성 후 Thymeleaf 예제용 컨트롤러 클래 ThymeleafExController 생성
    resources/templates 아래 thymeleafEx 폴더 생성 후 해당 폴더에 thymeleafEx01.html 파일 생성 (th:text="${data}")
    
### 3.2 Spring Boot Devtools

    - Spring Bot Devtools 는 애플리케이션 개발 시 유용한 기능들을 제공하는 모듈이며 개발 생산성 향상에 도움
    
    [Spring Boot Devtools 에서 제공하는 대표적인 기능]
    
    * Automatic Restart: classpath 에 있는 파일이 변경될 때마다 애플리케이션을 자동으로 재시작
    * Live Reload: 정적 자원(html, css, js) 수정 시 새로 고침 없이 바로 적용
    * Property Defaults: Thymeleaf 는 기본적으로 성능을 향상시키기 위해서 캐싱 기능을 사용하지만 개발 시 cache 기본값을 false 로 설정 가능
    
    - spring-boot-devtools 의존성 추가
    
3.2.1 Automatic Restart 적용하기

    - 설정법이 바뀜
    
    https://velog.io/@jodawooooon/IntelliJ-%EC%9E%90%EB%8F%99-%EB%B9%8C%EB%93%9C-%EC%84%A4%EC%A0%95-%EB%B0%A9%EB%B2%95-Registry%EC%97%90-compiler.automake.allow.when.app.running%EC%9D%B4-%EC%97%86%EB%8A%94-%EA%B2%BD%EC%9A%B0
    
3.2.2 Live Reload 적용하기

    - application.properties Live Reload 적용 설정 추가
        spring.devtools.livereload.enabled=true
    - 구글 크롬 웹 스토어에서 LiveReload 설치

3.2.3 Property Defaults 적용하기

    - 운영환경과 개발 환경의 application.properties 분리 후 운영환경에서는 캐싱 기능을 사용하고, 개발환경에서는 캐싱 기능을 꺼두는 방법으로 관리
      spring.thymeleaf.cache = false

### 3.3 Thymeleaf 예제 진행하기

3.3.1 th:text 예제

3.3.2 th:each 예제

3.3.3 th:if, th:unless 예제

3.3.4 th:switch, th:case 예제

3.3.5 th:href 예제





    
    
    
    
    
    
    
    
    

    
    

    

    



