package com.jojoldu.book.springboot.config.auth;


import com.jojoldu.book.springboot.domain.posts.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired()
    private  CustomOAuth2UserService customOAuth2UserService ;

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.csrf().disable()
                .headers().frameOptions().disable() ////h2 -console 화면을 사용 하기 위한 보안 해제 옵션
                .and().authorizeRequests() // Url별 권한관리에 시작점
                    .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**")
                    .permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()).anyRequest().authenticated()
                    .and().logout().logoutSuccessUrl("/")
                    .and().oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
                // 소셜로그인 성공시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
                // 리소스 서버(소셜 서비스)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있습니다.
    }
}
