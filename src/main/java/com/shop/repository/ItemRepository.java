package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository 를 상속받는 ItemRepository 작성.
// JpaRepository 는 2개의 제네릭 타입을 사용하는데 첫 번째에는 엔티티 타입 클래스를 넣어주고, 두 번째는 기본키 타입을 넣어줌.
public interface ItemRepository extends JpaRepository<Item, Long> {

}
