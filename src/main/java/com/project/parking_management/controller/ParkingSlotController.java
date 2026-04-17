package com.project.parking_management.controller;

import com.project.parking_management.entity.ParkingSlot;
import com.project.parking_management.service.ParkingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/slots")
public class ParkingSlotController {

    @Autowired
    private ParkingSlotService service;

    // ================= API (JSON) =================

    @ResponseBody
    @PostMapping("/api")
    public ParkingSlot addSlotApi(@RequestBody ParkingSlot slot) {
        return service.addSlot(slot);
    }

    @ResponseBody
    @GetMapping("/api")
    public List<ParkingSlot> getAllSlotsApi() {
        return service.getAllSlots();
    }

    @ResponseBody
    @GetMapping("/api/available")
    public List<ParkingSlot> getAvailableSlotsApi() {
        return service.getAvailableSlots();
    }

    // ================= UI (Thymeleaf) =================

    @GetMapping
    public String viewSlots(Model model) {
        model.addAttribute("slots", service.getAllSlots());
        return "slots"; // slots.html
    }

    @PostMapping("/add")
    public String addSlot(ParkingSlot slot) {
        service.addSlot(slot);
        return "redirect:/slots";
    }
}