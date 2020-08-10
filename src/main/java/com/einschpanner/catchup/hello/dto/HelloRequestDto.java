package com.einschpanner.catchup.hello.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HelloRequestDto {
    private String name;
    private int amount;

    public HelloRequestDto(String name, int amount){
        this.name = name;
        this.amount = amount;
    }
}
