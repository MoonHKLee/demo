package com.example.demo.repository;

import com.example.demo.domain.Schedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    List<Schedule> findAllSchedulesByScreenId(Long ScreenId);

    void deleteById(Long scheduleId);

    Schedule findScheduleById(Long scheduleId);

    @Query("SELECT s FROM Schedule s WHERE s.screen.id = :screenId")
    List<Schedule> findSchedulesByScreenId(Long screenId);

}
