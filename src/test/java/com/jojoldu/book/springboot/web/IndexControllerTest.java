package com.jojoldu.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.web.client.TestRestTemplate;


import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {

    private static Logger log = LoggerFactory.getLogger(IndexControllerTest.class);
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void MainPageLoading_Test(){

        //when
        String body =this.testRestTemplate.getForObject("/" ,String.class);
        log.info("============>" + body);
        //then
        assertThat(body).contains("스프링 부트");
    }

}