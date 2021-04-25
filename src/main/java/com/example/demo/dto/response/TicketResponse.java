package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TicketResponse {
    Long ticketId;
    Long screenId;
    String movieName;
    String startTime;
    Long seatNumber;
}
