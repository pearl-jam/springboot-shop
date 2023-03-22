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


