package com.shop.config;

import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// WebSecurityConfigurerAdapter 를 상속받는 클래스에 @EnableWebSecurity 어노테이션을 선언하면 SpringSecurityFilterChain 이 자동으로 포함
// WebSecurityConfigurerAdapter 를 상속받아서 메소드 오버라이딩을 통해 보안 설정을 커스터마이징
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;

    // http 요청에ㅔ 대한 보안을 설정
    // 페이지 권한 설정, 로그인 페이지 설정, 로그아웃 메소드 등에 대한 설정을 작성
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // 로그인 페이지 URL 설정
                .loginPage("/members/login")
                // 로그인 성공 시 이동할 URL 설정
                .defaultSuccessUrl("/")
                // 로그인 시 사용할 파라미터 이름을 email 지정
                .usernameParameter("email")
                // 로그인 실패 시 이동할 URL 설정
                .failureUrl("/members/login/error")
                .and()
                .logout()
                // 로그아웃 URL 설정
                .logoutRequestMatcher(new AntPathRequestMatcher(("/members/logout")))
                // 로그아웃 성공 시 이동할 URL 설정
                .logoutSuccessUrl("/");

        // 시큐리티 처리에 HttpServletRequest 를 이요한다는 것을 의미
        http.authorizeRequests()
                // permitAll()을 통해 모든 사용자가 인증(로그인)없이 해당 경로로 접근할 수 있도록 설정
                // 메인 페이지, 회원 관련 URL, 뒤에서 만들 상품 상세 페이지, 상품 이미지를 불러오는 경로
                .antMatchers("/h2-console/**").permitAll()
                .mvcMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
                // /admin 으로 시작하는 경로는 해당 계정이 ADMIN Role 경우에만 접근 가능하도록 설정
                .mvcMatchers("/admin/**").hasRole("ADMIN")

                .anyRequest().authenticated()
                .and().csrf().disable()
                .headers().frameOptions().disable();

        // 인증되지 않은 사용자가 리소스에 접근하였을 때 수행되는 핸들러를 등록
        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // static 디렉토리의 하위 파일은 인증을 무시하도록 설정
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    // 비밀번호를 데이터베이스에 그대로 저장했을 경우, 데이터베이스가 해킹당하면 고객의 회원 정보가 그대로 노출
    // 이를 해결하기 위해 BCryptPasswordEncoder 의 해시 함수를 이용하여 비밀번호를 암호화하여 저장
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    // Spring Security 에서 인증은 AuthenticationManager 를 통해 이러우지며 AuthenticationManagerBuilder 가 AuthenticationManager 를 생성
    // userDetailService 를 구현하고 있는 객체로 memberService 를 지정해주며, 비밀번호 암호화를 위해 passwordEncoder 를 지정
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService)
            .passwordEncoder(passwordEncoder());
    }
}
