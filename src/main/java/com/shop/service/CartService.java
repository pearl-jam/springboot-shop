package com.shop.service;

import com.shop.dto.CartDetailDto;
import com.shop.dto.CartItemDto;
import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.repository.CartItemRepository;
import com.shop.repository.CartRepository;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
// import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Long addCart(CartItemDto cartItemDto, String email) {
        // 장바구니에 담을 상품 엔티티 조회
        Item item = itemRepository.findById(cartItemDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        // 현재 로그인한 회원 엔티티 조회
        Member member = memberRepository.findByEmail(email);

        // 현재 로그인한 회원의 장바구니 엔티티 조회
        Cart cart = cartRepository.findByMemberId(member.getId());
        // 상품을 처음으로 장바구니에 담을 경우 해당 회원의 장바구니 엔티티를 생성
        if (cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        // 현재 상품이 장바구니에 이미 들어가 있는지 조회
        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

        if (savedCartItem != null) {
            // 장바구니에 이미 있던 상품일 경우 기존 수량에 현재 장바구니에 담을 수량 만큼 더해줌
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getId();
        } else {
            // 장바구니 엔티티, 상품 엔티티, 장바구니에 담을 수량을 이용하여 CartItem 엔티티 생성
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            // 장바구니에 들어갈 상품 저장
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }

    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String email) {
        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        
        // 현재 로그인한 회원의 장바구니 엔티티 조회
        Cart cart = cartRepository.findByMemberId(member.getId());

        // 장바구니에 상품을 한 번도 안 담았을 경우 장바구니 엔티티가 없으므로 빈 리스트를 반환
        // 장바구니에 담겨있는 상품 정보 조회
        return cart == null ? cartDetailDtoList : cartItemRepository.findCartDetailDtoList(cart.getId());
    }

    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String email) {
        // 현재 로그인한 회원을 조회
        Member curMember = memberRepository.findByEmail(email);
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        // 장바구니 상품을 저장한 회원을 조회
        Member savedMember = cartItem.getCart().getMember();

        // 현재 로그인한 회원과 장바구니 상품을 저장한 회원이 다를 경우 false 를 같으면 true 반환
        if (!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
            return false;
        }
        return true;
    }

    // 장바구니 상품의 수량을 업데이트하는 메소드
    public void updateCartItemCount(Long cartItemId, int count) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        cartItem.updateCount(count);
    }
}
