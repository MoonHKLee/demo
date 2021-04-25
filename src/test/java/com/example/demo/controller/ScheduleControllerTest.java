package com.example.demo.controller;


import com.example.demo.DemoApplicationTests;
import com.example.demo.dto.request.AddScheduleRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ScheduleControllerTest extends DemoApplicationTests {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Test
    @DisplayName("영화 상영등록 성공")
    void addSchedule_success() throws Exception {
        //given
        AddScheduleRequest body = AddScheduleRequest.builder()
            .movieName("태극기 휘날리며")
            .screenId(5L)
            .startTime("11:00")
            .build();

        //when
        MockHttpServletRequestBuilder builder = post("/schedule")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("X-User-Role", "admin")
            .content(objectMapper.writeValueAsString(body));

        //then
        mockMvc.perform(builder)
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("message").value("SUCCESS"))
            .andExpect(jsonPath("body").exists())
            .andExpect(jsonPath("body.scheduleId").value(25))
            .andExpect(jsonPath("body.screenId").value(5))
            .andExpect(jsonPath("body.movieName").value("태극기 휘날리며"))
            .andExpect(jsonPath("body.startTime").value("11:00"))
            .andDo(document("add-schedule"));
    }

    @Test
    @DisplayName("영화 상영등록 실패(권한 없음)")
    void addSchedule_failure1() throws Exception {
        //given
        AddScheduleRequest body = AddScheduleRequest.builder()
            .movieName("태극기 휘날리며")
            .screenId(5L)
            .startTime("13:00")
            .build();

        //when
        MockHttpServletRequestBuilder builder = post("/schedule")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(body));

        //then
        mockMvc.perform(builder)
            .andDo(print())
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("message").value("권한이 없습니다."))
            .andExpect(jsonPath("body").isEmpty());
    }

    @Test
    @DisplayName("영화 상영등록 실패(시간중복)")
    void addSchedule_failure2() throws Exception {
        //given
        AddScheduleRequest body = AddScheduleRequest.builder()
            .movieName("태극기 휘날리며")
            .screenId(2L)
            .startTime("18:00")
            .build();

        //when
        MockHttpServletRequestBuilder builder = post("/schedule")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("X-User-Role", "admin")
            .content(objectMapper.writeValueAsString(body));

        //then
        mockMvc.perform(builder)
            .andDo(print())
            .andExpect(status().isConflict())
            .andExpect(jsonPath("message").value("시간이 중복됩니다."))
            .andExpect(jsonPath("body").isEmpty());
    }

    @Test
    @DisplayName("영화 상영등록 실패(상영하지 않는 영화)")
    void addSchedule_failure3() throws Exception {
        //given
        AddScheduleRequest body = AddScheduleRequest.builder()
            .movieName("명량")
            .screenId(5L)
            .startTime("18:00")
            .build();

        //when
        MockHttpServletRequestBuilder builder = post("/schedule")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("X-User-Role", "admin")
            .content(objectMapper.writeValueAsString(body));

        //then
        mockMvc.perform(builder)
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("message").value("상영하지 않는 영화입니다."))
            .andExpect(jsonPath("body").isEmpty());
    }

    @Test
    @DisplayName("영화 상영 취소 성공")
    void removeSchedule_success() throws Exception {
        //given
        Long scheduleId = 2L;

        //when
        MockHttpServletRequestBuilder builder = delete("/schedule/{scheduleId}", scheduleId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("X-User-Role", "admin");

        //then
        mockMvc.perform(builder)
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("message").value("SUCCESS"))
            .andExpect(jsonPath("body").isEmpty())
            .andDo(document("remove-schedule"));
    }

    @Test
    @DisplayName("영화 상영 취소 실패(권한 없음)")
    void removeSchedule_failure1() throws Exception {
        //given
        Long scheduleId = 1L;

        //when
        MockHttpServletRequestBuilder builder = delete("/schedule/{scheduleId}", scheduleId)
            .contentType(MediaType.APPLICATION_JSON_VALUE);

        //then
        mockMvc.perform(builder)
            .andDo(print())
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("message").value("권한이 없습니다."))
            .andExpect(jsonPath("body").isEmpty());
    }

    @Test
    @DisplayName("영화 상영 취소 실패(1건 이상 예매됨)")
    void removeSchedule_failure2() throws Exception {
        //given
        Long scheduleId = 1L;

        //when
        MockHttpServletRequestBuilder builder = delete("/schedule/{scheduleId}", scheduleId)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("X-User-Role", "admin");

        //then
        mockMvc.perform(builder)
            .andDo(print())
            .andExpect(status().isConflict())
            .andExpect(jsonPath("message").value("예매한 사람이 있는 상영 영화입니다."))
            .andExpect(jsonPath("body").isEmpty());
    }

    @Test
    @DisplayName("영화 예매 성공")
    void buyTicket_success() throws Exception {
        //given
        Long scheduleId = 1L;
        Long seatNumber = 2L;

        //when
        MockHttpServletRequestBuilder builder = post(
            "/schedule/{scheduleId}/ticket/{seatNumber}", scheduleId, seatNumber
        )
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("X-User-Id", "foo");

        //then
        mockMvc.perform(builder)
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("message").value("SUCCESS"))
            .andExpect(jsonPath("body").exists())
            .andExpect(jsonPath("body.ticketId").value(2))
            .andExpect(jsonPath("body.screenId").value(1))
            .andExpect(jsonPath("body.movieName").value("태극기 휘날리며"))
            .andExpect(jsonPath("body.startTime").value("11:30"))
            .andExpect(jsonPath("body.seatNumber").value(2))
            .andDo(document("buy-ticket"));
    }

    @Test
    @DisplayName("영화 예매 실패(사용자 입력값 없음)")
    void buyTicket_failure1() throws Exception {
        //given
        Long scheduleId = 1L;
        Long seatNumber = 2L;

        //when
        MockHttpServletRequestBuilder builder = post(
            "/schedule/{scheduleId}/ticket/{seatNumber}", scheduleId, seatNumber
        )
            .contentType(MediaType.APPLICATION_JSON_VALUE);

        //then
        mockMvc.perform(builder)
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("message").value("유저 값이 없습니다."))
            .andExpect(jsonPath("body").isEmpty());
    }

    @Test
    @DisplayName("영화 예매 실패(예매된 자리)")
    void buyTicket_failure2() throws Exception {
        //given
        Long scheduleId = 1L;
        Long seatNumber = 1L;

        //when
        MockHttpServletRequestBuilder builder = post(
            "/schedule/{scheduleId}/ticket/{seatNumber}", scheduleId, seatNumber
        )
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("X-User-Id", "foo");

        //then
        mockMvc.perform(builder)
            .andDo(print())
            .andExpect(status().isConflict())
            .andExpect(jsonPath("message").value("이미 예매된 자리입니다."))
            .andExpect(jsonPath("body").isEmpty());
    }

    @Test
    @DisplayName("영화 예매 취소 성공")
    void cancelTicket_success() throws Exception {
        //given
        Long scheduleId = 1L;
        Long seatNumber = 1L;

        //when
        MockHttpServletRequestBuilder builder = delete(
            "/schedule/{scheduleId}/ticket/{seatNumber}", scheduleId, seatNumber
        )
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("X-User-Id", "foo");

        //then
        mockMvc.perform(builder)
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("message").value("SUCCESS"))
            .andExpect(jsonPath("body").isEmpty())
            .andDo(document("cancel-ticket"));
    }

    @Test
    @DisplayName("영화 예매 취소 실패(사용자 입력값 없음)")
    void cancelTicket_failure1() throws Exception {
        //given
        Long scheduleId = 1L;
        Long seatNumber = 1L;

        //when
        MockHttpServletRequestBuilder builder = delete(
            "/schedule/{scheduleId}/ticket/{seatNumber}", scheduleId, seatNumber
        )
            .contentType(MediaType.APPLICATION_JSON_VALUE);

        //then
        mockMvc.perform(builder)
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("message").value("유저 값이 없습니다."))
            .andExpect(jsonPath("body").isEmpty());
    }

    @Test
    @DisplayName("영화 예매 취소 실패(비어있는 좌석)")
    void cancelTicket_failure2() throws Exception {
        //given
        Long scheduleId = 1L;
        Long seatNumber = 3L;

        //when
        MockHttpServletRequestBuilder builder = delete(
            "/schedule/{scheduleId}/ticket/{seatNumber}", scheduleId, seatNumber
        )
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("X-User-Id", "foo");

        //then
        mockMvc.perform(builder)
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("message").value("예매한 자리가 아닙니다."))
            .andExpect(jsonPath("body").isEmpty());
    }

    @Test
    @DisplayName("영화 예매 취소 실패(권한 없음)")
    void cancelTicket_failure3() throws Exception {
        //given
        Long scheduleId = 1L;
        Long seatNumber = 1L;

        //when
        MockHttpServletRequestBuilder builder = delete(
            "/schedule/{scheduleId}/ticket/{seatNumber}", scheduleId, seatNumber
        )
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .header("X-User-Id", "bar");

        //then
        mockMvc.perform(builder)
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("message").value("예매한 자리가 아닙니다."))
            .andExpect(jsonPath("body").isEmpty());
    }
}
