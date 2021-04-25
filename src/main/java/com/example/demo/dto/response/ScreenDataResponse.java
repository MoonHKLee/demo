package com.example.demo.dto.response;

import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ScreenDataResponse {
    Long screenId;
    String movieName;
    String startTime;
    List<Long> bookedSeat;
}
