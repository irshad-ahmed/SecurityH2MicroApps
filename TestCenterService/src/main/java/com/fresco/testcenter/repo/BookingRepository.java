package com.fresco.testcenter.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fresco.testcenter.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}