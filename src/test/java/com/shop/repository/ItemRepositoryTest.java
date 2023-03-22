package com.shop.repository;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

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
}