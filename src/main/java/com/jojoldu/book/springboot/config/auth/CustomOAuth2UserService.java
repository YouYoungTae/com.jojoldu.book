package com.jojoldu.book.springboot.config.auth;


import com.jojoldu.book.springboot.config.auth.dto.OAuthAttributes;
import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import com.jojoldu.book.springboot.domain.posts.user.User;
import com.jojoldu.book.springboot.domain.posts.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {


    @Autowired()
    private  UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {


        OAuth2UserService<OAuth2UserRequest ,OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User =delegate.loadUser(userRequest);
        log.info("====================================loadUser error1:");
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("====================================loadUser error2:");
        log.info("====================================registrationId:" ,registrationId);

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        log.info("====================================loadUser error3:"+userNameAttributeName);
        OAuthAttributes attributes = OAuthAttributes.of( registrationId ,userNameAttributeName,oAuth2User.getAttributes());
        log.info("====================================loadUser error4:"+attributes);
        User user = saveOrUpdate(attributes);
        log.info("====================================loadUser saveOrUpdate:");
        log.info("====================================loadUser error7:" +attributes.getAttributes());
        log.info("====================================loadUser error8:" +userNameAttributeName);
        httpSession.setAttribute("user" , new SessionUser(user));
        if ( "response".equals( userNameAttributeName)) {
            userNameAttributeName = "id";
        }
        return new DefaultOAuth2User( Collections.singleton( new SimpleGrantedAuthority(user.getRoleKey()))
        , attributes.getAttributes()
        , userNameAttributeName);

    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map( entity -> entity.updates(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());
        return userRepository.save(user);
    }
}
