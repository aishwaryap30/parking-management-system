package com.project.parking_management.service;

import com.project.parking_management.entity.ParkingSlot;
import com.project.parking_management.entity.SlotStatus;
import com.project.parking_management.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSlotService {

    @Autowired
    private ParkingSlotRepository repository;

    public ParkingSlot addSlot(ParkingSlot slot) {
        return repository.save(slot);
    }

    public List<ParkingSlot> getAllSlots() {
        return repository.findAll();
    }

    public List<ParkingSlot> getAvailableSlots() {
        return repository.findByStatus(SlotStatus.AVAILABLE);
    }
}
