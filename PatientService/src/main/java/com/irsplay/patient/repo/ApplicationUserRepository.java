package com.irsplay.patient.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.irsplay.patient.model.ApplicationUser;

@Repository
public interface ApplicationUserRepository  extends JpaRepository<ApplicationUser, String>{

}