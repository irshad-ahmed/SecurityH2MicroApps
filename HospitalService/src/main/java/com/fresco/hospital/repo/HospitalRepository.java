package com.fresco.hospital.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fresco.hospital.model.Hospital;

@Repository
public interface HospitalRepository  extends JpaRepository<Hospital, Long>{

}