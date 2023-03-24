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
        

        
    
    
    
    
    

    
    

    

    



