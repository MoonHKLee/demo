package com.example.demo.dto.response;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AddScheduleResponse {
    Long scheduleId;
    Long screenId;
    String movieName;
    String startTime;
}
