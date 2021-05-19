package com.irsplay.testcenter.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.irsplay.testcenter.model.Booking;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Booking findByUser(String user);
    Booking findById(long bId);
    List<Booking> findByHospitalIdAndStatus(long hospitalId, String status);
}