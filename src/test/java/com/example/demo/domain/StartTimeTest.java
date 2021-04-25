package com.example.demo.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class StartTimeTest {

    @Test
    void isConflictedAt() {
        //given
        LocalTime startTime1 = LocalTime.of(13, 0);
        LocalTime startTime2 = LocalTime.of(14, 0);
        LocalTime startTime3 = LocalTime.of(15, 0);
        LocalTime startTime4 = LocalTime.of(16, 0);
        LocalTime startTime5 = LocalTime.of(17, 0);
        StartTime time1 = StartTime.builder().startTime(startTime3).build();

        //when
        boolean result1 = time1.isConflictedAt(startTime1);
        boolean result2 = time1.isConflictedAt(startTime2);
        boolean result3 = time1.isConflictedAt(startTime4);
        boolean result4 = time1.isConflictedAt(startTime5);

        //then
        assertFalse(result1);
        assertTrue(result2);
        assertTrue(result3);
        assertFalse(result4);
    }
}
