package com.project.parking_management.service;

import com.project.parking_management.entity.*;
import com.project.parking_management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepo;

    @Autowired
    private ParkingSlotRepository slotRepo;

    @Autowired
    private TicketRepository ticketRepo;

    @Autowired
    private BillingRepository billingRepo;

    public Ticket vehicleEntry(Vehicle vehicle) {

        // 1. Save vehicle
        Vehicle savedVehicle = vehicleRepo.save(vehicle);

        // 2. Find available slot
        List<ParkingSlot> availableSlots =
                slotRepo.findByStatus(SlotStatus.AVAILABLE);

        if (availableSlots.isEmpty()) {
            throw new RuntimeException("No slots available");
        }

        ParkingSlot slot = availableSlots.get(0);

        // 3. Mark slot occupied
        slot.setStatus(SlotStatus.OCCUPIED);
        slotRepo.save(slot);

        // 4. Create ticket
        Ticket ticket = new Ticket();
        ticket.setVehicle(savedVehicle);
        ticket.setSlot(slot);
        ticket.setEntryTime(LocalDateTime.now());
        ticket.setTicketCode("TKT-" + System.currentTimeMillis());

        return ticketRepo.save(ticket);
    }

    public Billing vehicleExit(Integer ticketId) {

        // 1. Find ticket
        Ticket ticket = ticketRepo.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        // 2. Set exit time
        ticket.setExitTime(LocalDateTime.now());
        ticket.setStatus(TicketStatus.CLOSED);

        // 3. Calculate duration
        long minutes = java.time.Duration.between(
                ticket.getEntryTime(),
                ticket.getExitTime()
        ).toMinutes();

        double hours = Math.ceil(minutes / 60.0); // minimum 1 hour

        // 4. Rate logic
        double rate = ticket.getVehicle().getVehicleType() == SlotType.FOUR_WHEELER ? 30 : 15;

        double amount = hours * rate;

        // 5. Save billing
        Billing billing = new Billing();
        billing.setTicket(ticket);
        billing.setDurationHours(hours);
        billing.setRatePerHour(rate);
        billing.setTotalAmount(amount);

        // 6. Free slot
        ParkingSlot slot = ticket.getSlot();
        slot.setStatus(SlotStatus.AVAILABLE);
        slotRepo.save(slot);

        // 7. Save ticket update
        ticketRepo.save(ticket);

        return billingRepo.save(billing);
    }
}
