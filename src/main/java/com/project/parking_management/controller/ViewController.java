package com.project.parking_management.controller;

import com.project.parking_management.dto.EntryResponse;
import com.project.parking_management.dto.ExitResponse;
import com.project.parking_management.repository.SlotRepository;
import com.project.parking_management.service.ParkingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ViewController {

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private SlotRepository slotRepository;

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
        model.addAttribute("slots", slotRepository.findAll());
        return "dashboard";
    }
}