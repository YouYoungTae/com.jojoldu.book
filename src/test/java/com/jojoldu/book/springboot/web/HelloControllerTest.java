package com.jojoldu.book.springboot.web;

import junit.framework.TestCase;
import org.assertj.core.internal.bytebuddy.matcher.ElementMatchers;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.is;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;




// 스프링부트테스트와 junit을 연결
@RunWith(SpringRunner.class)
//WebMvc 테스트에 집중 ,@Controller,@ControllerAdvice등을 사용가능
//단 @Service ,@Componet ,@Repository는 사용 불가능
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest extends TestCase {

    //웹 Api를 테스트할때 사용
    //테스트의 시작점
    @Autowired
    private MockMvc mvc;

    @Test
    public void helloTest() throws Exception {
        String hello ="hello";
        //get 메소드를 통해서 url에 요청 테스트
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }
    @Test
    public void helloDtoTest() throws Exception {
        String name="hello";
        int amount =1000;

        mvc.perform(get("/hello/dto")
                        .param("name",name)
                        .param("amount",String.valueOf(amount))
                    ).andExpect(status().isOk())
                    .andExpect(jsonPath("$.name" , Matchers.is(name)))
                    .andExpect(jsonPath("$.amount" , Matchers.is(amount)))
                ;
    }
}