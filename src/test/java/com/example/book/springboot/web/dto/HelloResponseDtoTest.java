package com.example.book.springboot.web.dto;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HelloResponseDtoTest {
    @Test
    public void lombokTest() {
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //then
        assertEquals(name, dto.getName());
        assertEquals(amount, dto.getAmount());
    }
}
