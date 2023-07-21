package com.saurav.DoctorApp.repository;

import com.saurav.DoctorApp.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepo extends JpaRepository<Doctor,Integer> {
}
