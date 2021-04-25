package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AddScheduleRequest {
    Long screenId;
    String movieName;
    String startTime;
}
