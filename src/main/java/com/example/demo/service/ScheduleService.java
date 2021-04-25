package com.example.demo.service;

import com.example.demo.common.HeaderRequest;
import com.example.demo.common.TransFormer;
import com.example.demo.domain.Movie;
import com.example.demo.domain.Schedule;
import com.example.demo.domain.Screen;
import com.example.demo.domain.StartTime;
import com.example.demo.domain.Ticket;
import com.example.demo.dto.request.AddScheduleRequest;
import com.example.demo.dto.response.ScreenDataResponse;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.NotAllowedException;
import com.example.demo.exception.TimeConflictException;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.repository.ScreenRepository;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScheduleService {


    @Autowired
    HeaderRequest headerRequest;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ScreenRepository screenRepository;

    @Autowired
    TransFormer transFormer;

    @Transactional
    public Schedule addSchedule(HttpServletRequest request, AddScheduleRequest body) {
        if (!headerRequest.isAdmin(request)) {
            throw new NotAllowedException("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        LocalTime inputTime = StartTime.toLocalTime(body.getStartTime());
        if (isConflicted(body.getScreenId(), inputTime)) {
            throw new TimeConflictException("시간이 중복됩니다.", HttpStatus.CONFLICT);
        }

        Movie movie = movieRepository.findMovieByMovieName(body.getMovieName());
        if (movie == null) {
            throw new BusinessException("상영하지 않는 영화입니다.", HttpStatus.BAD_REQUEST);
        }

        Screen screen = screenRepository.findScreenById(body.getScreenId());
        StartTime startTime = StartTime.builder().startTime(inputTime).build();

        Schedule schedule = Schedule.builder()
            .movie(movie)
            .screen(screen)
            .startTime(startTime)
            .build();

        return scheduleRepository.save(schedule);
    }

    @Transactional
    public void removeSchedule(HttpServletRequest request, Long scheduleId) {
        if (!headerRequest.isAdmin(request)) {
            throw new NotAllowedException("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        if (isBookedSchedule(scheduleId)) {
            throw new BusinessException("예매한 사람이 있는 상영 영화입니다.", HttpStatus.CONFLICT);
        }

        scheduleRepository.deleteById(scheduleId);
    }

    public List<ScreenDataResponse> showScreenData(Long screenId) {
        Screen screen = screenRepository.findScreenById(screenId);

        if (screen == null) {
            throw new BusinessException("존재하지 않는 상영관입니다.", HttpStatus.BAD_REQUEST);
        }

        List<Schedule> schedules = scheduleRepository.findSchedulesByScreenId(screenId);

        return transFormer.transformToScreenData(schedules);
    }

    private boolean isConflicted(Long screenId, LocalTime startTime) {
        List<Schedule> schedules = scheduleRepository.findAllSchedulesByScreenId(screenId);
        if (schedules == null) {
            return false;
        }

        for (Schedule schedule : schedules) {
            if (schedule.getStartTime().isConflictedAt(startTime)) {
                return true;
            }
        }
        return false;
    }

    private boolean isBookedSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findScheduleById(scheduleId);
        List<Ticket> tickets = schedule.getTickets();
        return tickets.size() > 0;
    }
}
