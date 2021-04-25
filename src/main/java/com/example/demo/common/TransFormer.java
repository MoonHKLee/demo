package com.example.demo.common;

import com.example.demo.domain.Schedule;
import com.example.demo.domain.Ticket;
import com.example.demo.dto.response.AddScheduleResponse;
import com.example.demo.dto.response.ScreenDataResponse;
import com.example.demo.dto.response.TicketResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TransFormer {

    public AddScheduleResponse transform(Schedule schedule) {
        return AddScheduleResponse.builder()
            .scheduleId(schedule.getId())
            .movieName(schedule.getMovie().getMovieName())
            .screenId(schedule.getScreen().getId())
            .startTime(schedule.getStartTime().getStartTime().toString())
            .build();
    }

    public TicketResponse transform(Ticket ticket,Schedule schedule) {
        return TicketResponse.builder()
            .ticketId(ticket.getId())
            .screenId(schedule.getScreen().getId())
            .movieName(schedule.getMovie().getMovieName())
            .startTime(schedule.getStartTime().getStartTime().toString())
            .seatNumber(ticket.getSeatNumber())
            .build();
    }

    public List<Long> transformToBookList(List<Ticket> tickets) {
        List<Long> result = new ArrayList<>();
        for (Ticket ticket : tickets) {
            result.add(ticket.getSeatNumber());
        }
        return result;
    }

    public List<TicketResponse> transform(List<Ticket> tickets) {
        List<TicketResponse> result = new ArrayList<>();
        for (Ticket ticket : tickets) {
            Schedule schedule = ticket.getSchedule();
            result.add(
                TicketResponse.builder()
                    .screenId(schedule.getScreen().getId())
                    .ticketId(ticket.getId())
                    .movieName(schedule.getMovie().getMovieName())
                    .startTime(schedule.getStartTime().getStartTime().toString())
                    .seatNumber(ticket.getSeatNumber())
                    .build()
            );
        }
        return result;
    }

    public List<ScreenDataResponse> transformToScreenData(List<Schedule> schedules) {
        List<ScreenDataResponse> screenData = new ArrayList<>();
        schedules.forEach(v -> screenData.add(
            ScreenDataResponse.builder()
                .screenId(v.getScreen().getId())
                .movieName(v.getMovie().getMovieName())
                .bookedSeat(transformToBookList(v.getTickets()))
                .startTime(v.getStartTime().getStartTime().toString())
                .build()
        ));
        return screenData;
    }
}
