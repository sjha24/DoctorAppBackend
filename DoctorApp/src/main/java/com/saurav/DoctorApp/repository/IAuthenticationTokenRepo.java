package com.saurav.DoctorApp.repository;

import com.saurav.DoctorApp.model.AuthenticationToken;
import com.saurav.DoctorApp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthenticationTokenRepo extends JpaRepository<AuthenticationToken,Integer> {
    AuthenticationToken findFirstByTokenValue(String authenticateToken);

    AuthenticationToken findFirstByPatient(Patient patient);
}
