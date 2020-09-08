package com.einschpanner.catchup.hello.api;

import com.einschpanner.catchup.hello.dao.HelloRepository;
import com.einschpanner.catchup.hello.domain.Hello;
import com.einschpanner.catchup.hello.dto.HelloRequestDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HelloRepository helloRepository;

    @Test
    @WithMockUser
    public void hello가_리턴된다() throws Exception {
        final String response = "hello";

        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
                .andDo(print());
    }

    @Test
    @WithMockUser
    public void helloDto가_리턴된다() throws Exception {
        final String name = "hello";
        final int amount = 100;

        mockMvc.perform(get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(name)))
                .andExpect(jsonPath("$.amount", Matchers.is(amount)))
                .andDo(print());
    }

    @Test
    public void 롬복_기능_테스트() {
        String name="test";
        int amount = 1000;

        HelloRequestDto dto = new HelloRequestDto(name, amount);

        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }

    @Test
    @WithMockUser
    public void h2_database_저장_테스트() throws Exception{
        Hello hello = Hello.builder()
                .name("woowon")
                .amount(10)
                .build();
        Hello helloSave = helloRepository.save(hello);

        Hello result = helloRepository.findById(helloSave.getId()).orElseThrow(() -> new RuntimeException("~~ 찾을 수 없습니다."));
        assertThat(result.getId()).isEqualTo(helloSave.getId());
        assertThat(result.getName()).isEqualTo(helloSave.getName());
        assertThat(result.getAmount()).isEqualTo(helloSave.getAmount());
    }
}