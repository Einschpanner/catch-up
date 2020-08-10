package com.einschpanner.catchup.hello.api;

import com.einschpanner.catchup.hello.dto.HelloRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {



    @GetMapping("/hello")
    public String Hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloRequestDto helloDto(HelloRequestDto dto) {
        log.debug("Get request param : {}", dto.getName());
        log.debug("Get request param : {}", dto.getAmount());
        return dto;
    }
}
