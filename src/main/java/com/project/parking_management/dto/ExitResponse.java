package com.project.parking_management.dto;

import java.time.LocalDateTime;

public class ExitResponse {

    private String ticketNumber;
    private String vehicleNumber;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private long amount;

    public ExitResponse(String ticketNumber, String vehicleNumber,
                        LocalDateTime entryTime, LocalDateTime exitTime,
                        long amount) {
        this.ticketNumber = ticketNumber;
        this.vehicleNumber = vehicleNumber;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.amount = amount;
    }

    public String getTicketNumber() { return ticketNumber; }
    public String getVehicleNumber() { return vehicleNumber; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }
    public long getAmount() { return amount; }
}