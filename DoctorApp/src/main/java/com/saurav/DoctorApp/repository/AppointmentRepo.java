package com.saurav.DoctorApp.repository;

import com.saurav.DoctorApp.model.Appointment;
import com.saurav.DoctorApp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepo extends JpaRepository<Appointment,Integer> {
    Appointment findFirstByPatient(Patient patient);
}
