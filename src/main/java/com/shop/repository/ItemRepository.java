package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository 를 상속받는 ItemRepository 작성.
// JpaRepository 는 2개의 제네릭 타입을 사용하는데 첫 번째에는 엔티티 타입 클래스를 넣어주고, 두 번째는 기본키 타입을 넣어줌.
public interface ItemRepository extends JpaRepository<Item, Long> {
    // 쿼리 메소드를 이용할 때 가장 많이 사용하는 문법으로 find 사용
    // 엔티티의 이름은 생략이 가능하며, By 뒤에는 검색할 때 사용할 변수의 이름 작성
    // find + (엔티티이름) + By + 변수 이름
    List<Item> findByItemNm(String itemNm);

    // 상품을 상품명과 상품 상세 설명을 OR 조건을 이용하여 조회하는 쿼리 메소드
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    // 파라미터로 넘어온 price 변수보다 값이 작은 상품 데이터를 조회하는 쿼리 메소드
    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);
}
