package com.project.parking_management.controller;

import com.project.parking_management.dto.EntryResponse;
import com.project.parking_management.dto.ExitResponse;
import com.project.parking_management.entity.Slot;
import com.project.parking_management.entity.Ticket;
import com.project.parking_management.repository.SlotRepository;
import com.project.parking_management.repository.TicketRepository;
import com.project.parking_management.service.ParkingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ViewController {

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private TicketRepository ticketRepository;

    // HOME
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // ENTRY PAGE
    @GetMapping("/entry")
    public String entryPage() {
        return "entry";
    }

    // HANDLE ENTRY
    @PostMapping("/entry")
    public String handleEntry(@RequestParam String vehicleNumber, Model model) {

        EntryResponse response = parkingService.vehicleEntry(vehicleNumber);

        model.addAttribute("ticket", response);
        return "entry";
    }

    // EXIT PAGE
    @GetMapping("/exit")
    public String exitPage() {
        return "exit";
    }

    // HANDLE EXIT
    @PostMapping("/exit")
    public String handleExit(@RequestParam String ticketNumber, Model model) {

        ExitResponse response = parkingService.vehicleExit(ticketNumber);

        model.addAttribute("bill", response);

        return "exit";
    }

    // DASHBOARD
    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        List<Slot> slots = slotRepository.findAll();

        // 🔥 NEW: Map slotId -> vehicleNumber
        Map<Long, String> slotVehicleMap = new HashMap<>();

        for (Slot slot : slots) {
            Optional<Ticket> ticketOpt =
                    ticketRepository.findActiveTicketBySlotId(slot.getId());

            if (ticketOpt.isPresent()) {
                slotVehicleMap.put(
                        slot.getId(),
                        ticketOpt.get().getVehicleNumber()
                );
            }
        }

        model.addAttribute("slots", slots);
        model.addAttribute("slotVehicleMap", slotVehicleMap); // 🔥 VERY IMPORTANT

        return "dashboard";
    }
//    @GetMapping("/dashboard")
//    public String dashboard(Model model) {
//        model.addAttribute("slots", slotRepository.findAll());
//        return "dashboard";
//    }

    // HISTORY PAGE
    @GetMapping("/history")
    public String historyPage(
            @RequestParam(required = false) String vehicleNumber,
            @RequestParam(required = false) String slotNumber,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "ACTIVE") String filterType,
            Model model) {

        List<Ticket> tickets;

        // PRIORITY 1: Vehicle search
        if (vehicleNumber != null && !vehicleNumber.isEmpty()) {

            tickets = ticketRepository
                    .findByVehicleNumberContainingIgnoreCase(vehicleNumber);

            // PRIORITY 2: Slot filter
        } else if (slotNumber != null && !slotNumber.isEmpty()) {

            tickets = ticketRepository
                    .findBySlot_SlotNumber(slotNumber);

            // PRIORITY 3: Date filter
        } else if (startDate != null && endDate != null
                && !startDate.isEmpty() && !endDate.isEmpty()) {

            // Convert String → LocalDate
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);

            // Convert to full day range
            LocalDateTime startDateTime = start.atStartOfDay();       // 00:00
            LocalDateTime endDateTime = end.atTime(23, 59, 59);       // 23:59:59

            // Apply filter type
            if (filterType.equals("ENTRY")) {

                tickets = ticketRepository
                        .findByEntryTimeBetween(startDateTime, endDateTime);

            } else if (filterType.equals("EXIT")) {

                tickets = ticketRepository
                        .findByExitTimeBetween(startDateTime, endDateTime);

            } else {

                tickets = ticketRepository
                        .findActiveDuring(startDateTime, endDateTime);
            }

            // DEFAULT: Show all
        } else {
            tickets = ticketRepository.findAll();
        }

        // Send data to UI
        model.addAttribute("tickets", tickets);
        model.addAttribute("vehicleNumber", vehicleNumber);
        model.addAttribute("slotNumber", slotNumber);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("filterType", filterType);

        return "history";
    }

}