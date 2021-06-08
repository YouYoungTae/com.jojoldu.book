package com.jojoldu.book.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

//getter 메서드 생성
@Getter 
// final이 선언된 필드는 생성자에 추가
@RequiredArgsConstructor
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
