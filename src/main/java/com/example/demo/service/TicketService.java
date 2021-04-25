package com.example.demo.service;

import com.example.demo.common.HeaderRequest;
import com.example.demo.common.TransFormer;
import com.example.demo.domain.Schedule;
import com.example.demo.domain.Ticket;
import com.example.demo.dto.response.TicketResponse;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.NoUserException;
import com.example.demo.exception.NotAllowedException;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.repository.TicketRepository;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketService {

    @Autowired
    HeaderRequest headerRequest;

    @Autowired
    TransFormer transFormer;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    TicketRepository ticketRepository;

    public TicketResponse buyTicket(HttpServletRequest request, Long scheduleId, Long seatNumber) {
        String userId = headerRequest.getUserId(request);
        if (userId == null) {
            throw new NoUserException("유저 값이 없습니다.", HttpStatus.BAD_REQUEST);
        }
        if (isBookedTicket(scheduleId,seatNumber)) {
            throw new BusinessException("이미 예매된 자리입니다.", HttpStatus.CONFLICT);
        }

        Schedule schedule = scheduleRepository.findScheduleById(scheduleId);

        Ticket ticket = Ticket.builder()
            .schedule(schedule)
            .seatNumber(seatNumber)
            .userId(userId)
            .build();

        ticket = ticketRepository.save(ticket);

        return transFormer.transform(ticket,schedule);
    }

    public void cancelTicket(HttpServletRequest request, Long scheduleId, Long seatNumber) {
        String userId = headerRequest.getUserId(request);
        if (userId == null) {
            throw new NoUserException("유저 값이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        Ticket ticket = ticketRepository.findTicketBySeatNumberAndScheduleId(seatNumber, scheduleId);

        if (ticket == null) {
            throw new NotAllowedException("예매한 자리가 아닙니다.", HttpStatus.BAD_REQUEST);
        }
        if(!ticket.getUserId().equals(userId)) {
            throw new NotAllowedException("예매한 자리가 아닙니다.", HttpStatus.BAD_REQUEST);
        }
        ticketRepository.delete(ticket);
    }

    public List<TicketResponse> getMyTickets(HttpServletRequest request) {
        String userId = headerRequest.getUserId(request);
        if (userId==null) {
            throw new NoUserException("유저 값이 없습니다.", HttpStatus.BAD_REQUEST);
        }
        List<Ticket> myTickets = ticketRepository.findTicketsByUserId(userId);
        return transFormer.transform(myTickets);
    }

    private boolean isBookedTicket(Long scheduleId, Long seatNumber) {
        Schedule schedule = scheduleRepository.findScheduleById(scheduleId);
        List<Ticket> tickets = schedule.getTickets();
        if (tickets == null) {
            return false;
        }

        for (Ticket ticket : tickets) {
            if (ticket.getSeatNumber().equals(seatNumber)) {
                return true;
            }
        }
        return false;
    }
}
