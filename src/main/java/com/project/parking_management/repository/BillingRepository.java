package com.project.parking_management.repository;

import com.project.parking_management.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRepository extends JpaRepository<Billing, Integer> {

}
