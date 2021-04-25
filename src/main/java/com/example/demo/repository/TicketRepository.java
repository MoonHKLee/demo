package com.example.demo.repository;

import com.example.demo.domain.Ticket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    void deleteTicketById(Long ticketId);

    @Query("SELECT t FROM Ticket t WHERE t.seatNumber = :seatNumber AND t.schedule.id = :scheduleId")
    Ticket findTicketBySeatNumberAndScheduleId(Long seatNumber, Long scheduleId);

    List<Ticket> findTicketsByUserId(String userId);
}
