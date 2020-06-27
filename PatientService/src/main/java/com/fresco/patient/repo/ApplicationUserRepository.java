package com.fresco.patient.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fresco.patient.model.ApplicationUser;

@Repository
public interface ApplicationUserRepository  extends JpaRepository<ApplicationUser, String>{

}