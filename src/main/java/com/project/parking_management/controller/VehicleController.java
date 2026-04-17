package com.project.parking_management.controller;


import com.project.parking_management.entity.Billing;
import com.project.parking_management.entity.Ticket;
import com.project.parking_management.entity.Vehicle;
import com.project.parking_management.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService service;

    @ResponseBody
    @PostMapping("/entry")
    public Ticket vehicleEntry(@RequestBody Vehicle vehicle) {
        return service.vehicleEntry(vehicle);
    }

    @ResponseBody
    @PostMapping("/exit/{ticketId}")
    public Billing vehicleExit(@PathVariable Integer ticketId) {
        return service.vehicleExit(ticketId);
    }
}