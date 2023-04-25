package com.shop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MainItemDto {
    private Long id;

    private String itemNm;

    private String itemDetail;

    private String imgUrl;

    private Integer price;

    // 생성자에 @QueryProjection 어노테이션을 선언하여 Querydsl 로 결과 조회 시 MainItemDto 객체로 바로 받아오도록 활용
    // @QueryProject 을 사용할 때 [maven compile]을 실행 해 QDto 파일을 생성해야 함
    @QueryProjection
    public MainItemDto(Long id, String itenNm, String itemDetail, String imgUrl, Integer price) {
        this.id = id;
        this.itemNm = itenNm;
        this.itemDetail = itemDetail;
        this.imgUrl = imgUrl;
        this.price = price;
    }
}
