package com.project.parking_management.repository;

import com.project.parking_management.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Find ACTIVE ticket by ticket number
    Optional<Ticket> findByTicketNumberAndStatus(String ticketNumber, String status);
    Optional<Ticket> findByTicketNumber(String ticketNumber);
}
