package com.shop.repository;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

// 통합 테스트를 위해 스프링 부트에서 제공하는 어노테이션
// 실제 애플리케이션을 구동할 때처럼 모든 Bean 을 IoC 컨테이너에 등록
@SpringBootTest
// 테스트 코드 실행 시 application-test.properties 에 같은 설정이 있따면 높은 우선순위를 부여 (H2 데이터베이스 사용)
@TestPropertySource(locations="classpath:application-test.properties")
class ItemRepositoryTest {
    // ItemRepository 를 사용하기 위해서 @Autowired 어노테이션을 이용하여 Bean 을 주입
    @Autowired
    ItemRepository itemRepository;

    // 테스트할 메소드 위에 선언하여 해당 메소드를 테스트 대상으로 지정
    @Test
    // Junit5에 추가된 어노테이션으로 테스트 코드 실행 시 @DisplayName 에 지정한 테스트명이 노출
    @DisplayName(("상품 저장 테스트"))
    public void createItemTest() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }

    // 테스트 코드 실행 시 데이터베이스에 상품 데이터가 없으므로 테스트 데이터 생성을 위해 10개의 삼품을 저장하는 메소드를 작성하여 findByItemNmTest()에서 실행
    public void createItemList() {
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest() {
        // 상품을 만드는 메소드 실행하여 조회할 대상 생성
        this.createItemList();

        // ItemRepository 인터페이스에 작성했던 findByItemNm 메소드를 호출. 파라미터로는 "테스트 상품1"이라는 상품명을 전달
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
        
        // 조회 결과 얻은 item 객체들을 출력
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByItemNmOrItemDetailTest() {
        // 상품을 만드는 메소드 실행하여 조회할 대상 생성
        this.createItemList();

        // 상품명이 "테스트 상품1" 또는 상품 상세 설명이 "테스트 상품 상세 설명5"이면 해당 상품을 itemList 에 할당
        // 테스트 코드를 실행하면 조건대로 2개의 상품이 출력되는 것을 확인
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest() {
        this.createItemList();

        // 현재 데이터베이스에 저장된 가격은 10001~10010. 테스트 코드 실행 시 10개의 상품을 저장하는 로그가 콘솔에 나타나고 마지막에 가격이 10005보다 작은 4개의 상품을 출력
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDesc() {
        this.createItemList();

        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }
}