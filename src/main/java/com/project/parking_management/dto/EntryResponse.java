package com.project.parking_management.dto;

import java.time.LocalDateTime;

public class EntryResponse {

    private String ticketNumber;
    private String vehicleNumber;
    private String slotNumber;
    private LocalDateTime entryTime;

    public EntryResponse(String ticketNumber, String vehicleNumber,
                         String slotNumber, LocalDateTime entryTime) {
        this.ticketNumber = ticketNumber;
        this.vehicleNumber = vehicleNumber;
        this.slotNumber = slotNumber;
        this.entryTime = entryTime;
    }

    public String getTicketNumber() { return ticketNumber; }
    public String getVehicleNumber() { return vehicleNumber; }
    public String getSlotNumber() { return slotNumber; }
    public LocalDateTime getEntryTime() { return entryTime; }
}