package com.project.parking_management.controller;
import com.project.parking_management.dto.EntryResponse;
import com.project.parking_management.dto.ExitResponse;
import com.project.parking_management.entity.Slot;
import com.project.parking_management.entity.Ticket;
import com.project.parking_management.repository.SlotRepository;
import com.project.parking_management.service.ParkingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private SlotRepository slotRepository;

    // =========================
    // ENTRY API
    // =========================
    @PostMapping("/entry")
    public EntryResponse entry(@RequestParam String vehicleNumber) {
        return parkingService.vehicleEntry(vehicleNumber);
    }

    // =========================
    // EXIT API
    // =========================
    @PostMapping("/exit")
    public ExitResponse exit(@RequestParam String ticketNumber) {
        return parkingService.vehicleExit(ticketNumber);
    }

    // =========================
    // VIEW ALL SLOTS
    // =========================
    @GetMapping("/slots")
    public List<Slot> getAllSlots() {
        return slotRepository.findAll();
    }
}
