package com.example.demo.controller;

import com.example.demo.common.TransFormer;
import com.example.demo.domain.Schedule;
import com.example.demo.domain.Ticket;
import com.example.demo.dto.request.AddScheduleRequest;
import com.example.demo.dto.response.Response;
import com.example.demo.dto.response.TicketResponse;
import com.example.demo.service.ScheduleService;
import com.example.demo.service.TicketService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/schedule", consumes = MediaType.APPLICATION_JSON_VALUE)
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    TicketService ticketService;

    @Autowired
    TransFormer transFormer;

    @PostMapping(path = "")
    public ResponseEntity<Response> addSchedule(
        HttpServletRequest request,
        @RequestBody AddScheduleRequest body
    ) {
        Schedule newSchedule = scheduleService.addSchedule(request, body);

        Response response = Response.builder()
            .message("SUCCESS")
            .body(transFormer.transform(newSchedule))
            .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{scheduleId}")
    public ResponseEntity<Response> removeSchedule(
        HttpServletRequest request,
        @PathVariable Long scheduleId
    ) {
        scheduleService.removeSchedule(request, scheduleId);

        Response response = Response.builder()
            .message("SUCCESS")
            .body(null)
            .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/{scheduleId}/ticket/{seatNumber}")
    public ResponseEntity<Response> buyTicket(
        HttpServletRequest request,
        @PathVariable Long scheduleId,
        @PathVariable Long seatNumber
    ) {
        TicketResponse ticket = ticketService.buyTicket(request, scheduleId, seatNumber);

        Response response = Response.builder()
            .message("SUCCESS")
            .body(ticket)
            .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{scheduleId}/ticket/{seatNumber}")
    public ResponseEntity<Response> cancelTicket(
        HttpServletRequest request,
        @PathVariable Long scheduleId,
        @PathVariable Long seatNumber
    ) {
        ticketService.cancelTicket(request,scheduleId,seatNumber);

        Response response = Response.builder()
            .message("SUCCESS")
            .body(null)
            .build();
        return ResponseEntity.ok(response);
    }
}
