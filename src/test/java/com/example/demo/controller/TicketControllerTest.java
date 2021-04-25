package com.example.demo.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import com.example.demo.DemoApplicationTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

class TicketControllerTest extends DemoApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("내 예매 조회 성공")
    void getMyTickets_success() throws Exception {
        //given

        //when
        MockHttpServletRequestBuilder builder = get("/ticket")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("X-User-Id", "foo");

        //then
        mockMvc.perform(builder)
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("message").value("SUCCESS"))
            .andExpect(jsonPath("body",hasSize(1)))
            .andDo(document("my-tickets"));
    }

    @Test
    @DisplayName("내 예매 조회 실패(사용자 없음)")
    void getMyTickets_failure1() throws Exception {
        //given

        //when
        MockHttpServletRequestBuilder builder = get("/ticket")
            .contentType(MediaType.APPLICATION_JSON_VALUE);

        //then
        mockMvc.perform(builder)
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("message").value("유저 값이 없습니다."))
            .andExpect(jsonPath("body").isEmpty());
    }
}
