package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.hamcrest.Matchers.hasSize;

import com.example.demo.DemoApplicationTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

class ScreenControllerTest extends DemoApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("상영관 조회 성공")
    void getScreenData_success() throws Exception {
        //given
        Long screenId = 1L;

        //when
        MockHttpServletRequestBuilder builder = get("/screen/{screenId}", screenId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("X-User-Id", "foo");

        //then
        mockMvc.perform(builder)
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("message").value("SUCCESS"))
            .andExpect(jsonPath("body",hasSize(6)))
            .andDo(document("show-screen"));;
    }

    @Test
    @DisplayName("상영관 조회 실패")
    void getScreenData_failure1() throws Exception {
        //given
        Long screenId = 11L;

        //when
        MockHttpServletRequestBuilder builder = get("/screen/{screenId}", screenId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("X-User-Id", "foo");

        //then
        mockMvc.perform(builder)
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("message").value("존재하지 않는 상영관입니다."))
            .andExpect(jsonPath("body").isEmpty());
    }
}
