package com.saurav.DoctorApp.controller;

import com.saurav.DoctorApp.model.Appointment;
import com.saurav.DoctorApp.repository.AppointmentRepo;
import com.saurav.DoctorApp.service.AppointmentService;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("appointment")
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;
    @GetMapping
    public List<Appointment>getAllAppointments(){
        return appointmentService.getAllAppointments();
    }
}
