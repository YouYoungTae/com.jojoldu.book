package com.jojoldu.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
// jpa상 테이블과 연결되는 클래스
// java의 카멜케이스 이름을  언더스코어 네이밍으로 테이블 이름을 매칭한다.
// ex) SalesManager -> sales_manager
@Entity
public class Posts {

    //    pk
    @Id
    // autoincrement
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    // 칼럼
    @Column(length =500 ,nullable = false)
    private String title;

    @Column(columnDefinition ="Text" ,nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts( String title,String content, String author){
        this.title =title;
        this.content =content;
        this.author = author;
    }
}
