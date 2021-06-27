package com.jojoldu.book.springboot.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@ComponentScan
@EnableAspectJAutoProxy
public class AspectjConfig {
}
