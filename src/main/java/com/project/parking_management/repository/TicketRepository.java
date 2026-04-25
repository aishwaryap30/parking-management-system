package com.project.parking_management.repository;

import com.project.parking_management.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Find ACTIVE ticket by ticket number
    Optional<Ticket> findByTicketNumberAndStatus(String ticketNumber, String status);
    Optional<Ticket> findByTicketNumber(String ticketNumber);
    List<Ticket> findByVehicleNumberContainingIgnoreCase(String vehicleNumber);

    List<Ticket> findBySlot_SlotNumber(String slotNumber);


    @Query("SELECT t FROM Ticket t WHERE t.entryTime <= :end AND (t.exitTime IS NULL OR t.exitTime >= :start)")
    List<Ticket> findActiveDuring(
            @org.springframework.data.repository.query.Param("start") LocalDateTime start,
            @org.springframework.data.repository.query.Param("end") LocalDateTime end
    );

    List<Ticket> findByEntryTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Ticket> findByExitTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT t FROM Ticket t WHERE t.slot.id = :slotId AND t.status = 'ACTIVE'")
    Optional<Ticket> findActiveTicketBySlotId(Long slotId);
}
