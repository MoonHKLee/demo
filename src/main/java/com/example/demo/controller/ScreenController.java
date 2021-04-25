package com.example.demo.controller;

import com.example.demo.domain.Schedule;
import com.example.demo.dto.response.Response;
import com.example.demo.dto.response.ScreenDataResponse;
import com.example.demo.service.ScheduleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/screen", consumes = MediaType.APPLICATION_JSON_VALUE)
public class ScreenController {

    @Autowired
    ScheduleService scheduleService;

    @GetMapping(path = "/{screenId}")
    public ResponseEntity<Response> getScreenData(
        @PathVariable Long screenId
    ) {
        List<ScreenDataResponse> schedules = scheduleService.showScreenData(screenId);

        Response response = Response.builder()
            .message("SUCCESS")
            .body(schedules)
            .build();
        return ResponseEntity.ok(response);
    }
}
