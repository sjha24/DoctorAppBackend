package com.saurav.DoctorApp.controller;

import com.saurav.DoctorApp.model.Doctor;
import com.saurav.DoctorApp.repository.DoctorRepo;
import com.saurav.DoctorApp.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("doctor")
public class DoctorController {
    @Autowired
    DoctorService doctorService;
    @PostMapping
    public String addDoctor(@RequestBody Doctor doctor){
        return doctorService.addDoctor(doctor);
    }
    @GetMapping
    public List<Doctor>getAllDoctors(){
        return doctorService.getAllDoctors();
    }
}
