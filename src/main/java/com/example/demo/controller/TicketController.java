package com.example.demo.controller;

import com.example.demo.common.HeaderRequest;
import com.example.demo.domain.Ticket;
import com.example.demo.dto.response.Response;
import com.example.demo.dto.response.TicketResponse;
import com.example.demo.service.TicketService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/ticket", consumes = MediaType.APPLICATION_JSON_VALUE)
public class TicketController {

    @Autowired
    HeaderRequest headerRequest;

    @Autowired
    TicketService ticketService;

    @GetMapping(path = "")
    public ResponseEntity<Response> getMyTickets(
        HttpServletRequest request
    ) {
        List<TicketResponse> myTickets = ticketService.getMyTickets(request);
        Response response = Response.builder()
            .message("SUCCESS")
            .body(myTickets)
            .build();
        return ResponseEntity.ok(response);
    }
}
