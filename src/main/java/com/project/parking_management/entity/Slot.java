package com.project.parking_management.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String slotNumber;

    private String status; // FREE / OCCUPIED

    // Constructors
    public Slot() {}

    public Slot(String slotNumber, String status) {
        this.slotNumber = slotNumber;
        this.status = status;
    }

    // Getters & Setters
    public Long getId() { return id; }

    public String getSlotNumber() { return slotNumber; }
    public void setSlotNumber(String slotNumber) { this.slotNumber = slotNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

