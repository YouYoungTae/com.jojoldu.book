package com.jojoldu.book.springboot.config.auth.dto;

import com.jojoldu.book.springboot.domain.posts.user.Role;
import com.jojoldu.book.springboot.domain.posts.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Builder
@Getter
@ToString
@Slf4j
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes (Map<String,Object> attributes ,
                            String nameAttributeKey,
                            String name,
                            String email,
                            String picture){
        this.attributes = attributes;
        this.name = name;
        this.email = email;
    }

    /**
     *
     * @param registrationId
     * @param userNameAttributeName
     * @param attributes
     * @return
     */
    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String,Object> attributes){
        if ("naver".equals(registrationId)) {
            log.info("OAuthAttributes of Name " ,userNameAttributeName);
            log.info("OAuthAttributes of map  ");
            attributes.keySet().forEach( e -> {
                log.info( "key:" + e);
                log.info( "value:" +  attributes.get(e));
            });
            return ofNaver("id",attributes);
        }
        return ofGoogle(userNameAttributeName ,attributes);
    }

    public static OAuthAttributes ofGoogle( String userNameAttributeName,
                                            Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String,Object> response = (Map<String, Object>) attributes.get("response");
        log.info("ofNaver" ,response);
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    /**
     *
     * @return
     */
    public User toEntity(){
        return  User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }

}
