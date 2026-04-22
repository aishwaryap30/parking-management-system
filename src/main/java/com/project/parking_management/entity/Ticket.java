package com.project.parking_management.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticketNumber;

    private String vehicleNumber;

    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    private String status; // ACTIVE / CLOSED

    // 🔗 Many Tickets → One Slot
    @ManyToOne
    @JoinColumn(name = "slot_id")
    private Slot slot;

    // Constructors
    public Ticket() {}

    // Getters & Setters

    public Long getId() { return id; }

    public String getTicketNumber() { return ticketNumber; }
    public void setTicketNumber(String ticketNumber) { this.ticketNumber = ticketNumber; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public LocalDateTime getEntryTime() { return entryTime; }
    public void setEntryTime(LocalDateTime entryTime) { this.entryTime = entryTime; }

    public LocalDateTime getExitTime() { return exitTime; }
    public void setExitTime(LocalDateTime exitTime) { this.exitTime = exitTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Slot getSlot() { return slot; }
    public void setSlot(Slot slot) { this.slot = slot; }
}
