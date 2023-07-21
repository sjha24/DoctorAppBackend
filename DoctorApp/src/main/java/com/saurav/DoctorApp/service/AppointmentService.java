package com.saurav.DoctorApp.service;
import com.saurav.DoctorApp.model.Appointment;
import com.saurav.DoctorApp.model.Patient;
import com.saurav.DoctorApp.repository.AppointmentRepo;
import com.saurav.DoctorApp.repository.DoctorRepo;
import com.saurav.DoctorApp.repository.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepo appointmentRepo;
    @Autowired
    DoctorRepo doctorRepo;
    @Autowired
    PatientRepo patientRepo;

    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }
    public void saveAppointment(Appointment appointment){
        appointment.setAppointmentCreationTime(LocalDateTime.now());
        appointmentRepo.save(appointment);
    }

    public Appointment getAppointmentForPatient(Patient patient) {
        return appointmentRepo.findFirstByPatient(patient);
    }

    public void cancelAppointment(Appointment appointment) {
        appointmentRepo.delete(appointment);
    }
}
