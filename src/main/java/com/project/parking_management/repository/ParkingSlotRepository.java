package com.project.parking_management.repository;

import com.project.parking_management.entity.ParkingSlot;
import com.project.parking_management.entity.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Integer> {
    List<ParkingSlot> findByStatus(SlotStatus status);
}
