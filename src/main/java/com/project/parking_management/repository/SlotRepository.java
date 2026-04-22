package com.project.parking_management.repository;

import com.project.parking_management.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    // Find first FREE slot
    Optional<Slot> findFirstByStatus(String status);
}
