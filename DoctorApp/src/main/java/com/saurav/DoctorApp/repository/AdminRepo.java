package com.saurav.DoctorApp.repository;

import com.saurav.DoctorApp.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin,Integer> {
}
