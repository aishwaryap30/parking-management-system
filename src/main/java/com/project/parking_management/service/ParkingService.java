package com.project.parking_management.service;

import com.project.parking_management.dto.EntryResponse;
import com.project.parking_management.dto.ExitResponse;
import com.project.parking_management.entity.Slot;
import com.project.parking_management.entity.Ticket;
import com.project.parking_management.repository.SlotRepository;
import com.project.parking_management.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ParkingService {

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private TicketRepository ticketRepository;

    // =========================
    // VEHICLE ENTRY
    // =========================
    public EntryResponse vehicleEntry(String vehicleNumber) {

        // 1. Find FREE slot
        Optional<Slot> optionalSlot = slotRepository.findFirstByStatus("FREE");

        if (!optionalSlot.isPresent()) {
            throw new RuntimeException("Parking Full");
        }

        Slot slot = optionalSlot.get();

        // 2. Generate Ticket Number
        String ticketNumber = "T" + System.currentTimeMillis();

        // 3. Create Ticket
        Ticket ticket = new Ticket();
        ticket.setTicketNumber(ticketNumber);
        ticket.setVehicleNumber(vehicleNumber);
        ticket.setEntryTime(LocalDateTime.now());
        ticket.setStatus("ACTIVE");
        ticket.setSlot(slot);

        // 4. Mark Slot OCCUPIED
        slot.setStatus("OCCUPIED");
        slotRepository.save(slot);

        // 5. Save Ticket
        ticketRepository.save(ticket);

        return new EntryResponse(
                ticketNumber,
                vehicleNumber,
                slot.getSlotNumber(),
                ticket.getEntryTime()
        );
    }

    // =========================
    // VEHICLE EXIT
    // =========================
    public ExitResponse vehicleExit(String ticketNumber) {

        // 1. Find ACTIVE ticket
        Optional<Ticket> optionalTicket =
                ticketRepository.findByTicketNumberAndStatus(ticketNumber, "ACTIVE");

        if (!optionalTicket.isPresent()) {
            throw new RuntimeException("Invalid Ticket or Already Closed");
        }

        Ticket ticket = optionalTicket.get();

        // 2. Set exit time
        ticket.setExitTime(LocalDateTime.now());

        // 3. Calculate duration
        Duration duration = Duration.between(ticket.getEntryTime(), ticket.getExitTime());
        long hours = duration.toHours();

        if (hours == 0) {
            hours = 1; // minimum 1 hour charge
        }

        // 4. Calculate amount
        int ratePerHour = 20;
        long amount = hours * ratePerHour;

        // 5. Update ticket
        ticket.setStatus("CLOSED");
        ticketRepository.save(ticket);

        // 6. Free slot
        Slot slot = ticket.getSlot();
        slot.setStatus("FREE");
        slotRepository.save(slot);

        // 7. Return response
        return new ExitResponse(
                ticket.getTicketNumber(),
                ticket.getVehicleNumber(),
                ticket.getEntryTime(),
                ticket.getExitTime(),
                amount
        );
    }
}