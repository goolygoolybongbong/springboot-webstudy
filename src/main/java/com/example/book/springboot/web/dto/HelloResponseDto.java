package com.example.book.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
// final이 붙은 모든 프로퍼티가 포함된 생성자를 만듦, DTO는 Spring bean이 될 수 없는가?
@RequiredArgsConstructor
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
