package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

// Item 클래스를 entity 선언. 또한 @Table 어노테이션을 통해 어떤 테이블과 매핑될지를 지정
// Item 테이블과 매핑되도록 name item 지정
@Entity
@Table(name="item")
@Getter
@Setter
@ToString
public class Item {
    // entity 선언한 클래스는 반드시 기본키를 가져야 함.
    // 기본키가 되는 멤버변수에 @id 어노테이션을 붙여줌.
    // 그리고 테이블에 매핑될 컬럼의 이름을 @Column 어노테이션을 통해 설정
    // item 클래스의 id 변수와 item 테이블의 item_id 컬럼이 매핑.
    // 마지막으로 @GeneratedValue 어노테이션을 통해 기본키 생성 전략을 AUTO 지정
    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 상품 코드

    @Column(nullable = false, length = 50)
    private String itemNm; // 상품명

    @Column(name="price", nullable = false)
    private int price; // 가격
    
    @Column(nullable = false)
    private int stockNumber; // 재고수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; // 상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // 상품 판매 상태

    private LocalDateTime regTime; // 등록시간

    private LocalDateTime updateTime; // 수정시간
}
