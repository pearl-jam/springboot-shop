package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
public class Member {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    // 회원은 이메일을 통해 유일하게 구분해야 하기 때문에, 동일한 값이 데이터베이스에 들어올 수 없도록 unique 속성을 지정
    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    // 자바의 enum 타입을 엔티티의 속성으로 지정할 수 있음.
    // Enum 을 사용할 때 기본적으로 순서가 저장되는데, enum 의 순서가 바뀔 경우 문제가 발생할 수 있으므로 "EnumType.STRING" 옵션을 사용해서 String 으로 저장 권장
    @Enumerated(EnumType.STRING)
    private Role role;

    // Member 엔티티를 생성하는 메소드
    // Member 엔티티에 회원을 생성하는 메소드를 만들어서 관리한다면 코드가 변경되더라도 한 군데만 수정
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        // 스프링 시큐리티 설정 클래스에 등록한 BCryptPasswordEncoder Bean 을 파라미터로 넘겨서 비밀번호를 암호화
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword((password));
        member.setRole(Role.ADMIN);
        return member;
    }
}
