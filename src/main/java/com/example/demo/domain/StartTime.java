package com.example.demo.domain;

import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class StartTime {

    @Column(name = "start_time")
    private LocalTime startTime;

    public boolean isConflictedAt(LocalTime startTime) {
        if (this.startTime.isBefore(startTime)) {
            return this.startTime.plusHours(2L).isAfter(startTime);
        } else if (this.startTime.isAfter(startTime)){
            return this.startTime.minusHours(2L).isBefore(startTime);
        } else {
            return true;
        }
    }

    public static LocalTime toLocalTime(String time) {
        String[] split = time.split(":");
        return LocalTime.of(Integer.parseInt(split[0]),Integer.parseInt(split[1]));
    }
}
