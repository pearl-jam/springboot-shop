package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

// JpaRepository 를 상속받는 ItemRepository 작성.
// JpaRepository 는 2개의 제네릭 타입을 사용하는데 첫 번째에는 엔티티 타입 클래스를 넣어주고, 두 번째는 기본키 타입을 넣어줌.
// 2.7 Spring DATA JPA Querydsl
// - QueryDslPredicateExecutor 인터페이스 상속 추가
public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {
    // 쿼리 메소드를 이용할 때 가장 많이 사용하는 문법으로 find 사용
    // 엔티티의 이름은 생략이 가능하며, By 뒤에는 검색할 때 사용할 변수의 이름 작성
    // find + (엔티티이름) + By + 변수 이름
    List<Item> findByItemNm(String itemNm);

    // 상품을 상품명과 상품 상세 설명을 OR 조건을 이용하여 조회하는 쿼리 메소드
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    // 파라미터로 넘어온 price 변수보다 값이 작은 상품 데이터를 조회하는 쿼리 메소드
    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    // @Query 어노테이션 안에 JPQL 로 쿼리문 작성
    // from 뒤에는 엔티티 클래스로 작성한 Item 을 지정해주었고, Item 으로부터 데이터를 select 하겠다는 것을 의미
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    // 파라미터 @Param 어노테이션을 이용하여 파라미터로 넘어온 값을 JPQL 에 들어갈 변수로 지정
    // 현재 itemDetail 변수를 "like % %" 사이에 ":itemDetail" 로 값이 들어가도록 작성
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    // 기존의 데이터베이스에서 사용하던 쿼리를 그대로 사용해야 할 때는 @Query 의 nativeQuery 속성을 사용하면 기존 쿼리 그대로 활용
    @Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
}
