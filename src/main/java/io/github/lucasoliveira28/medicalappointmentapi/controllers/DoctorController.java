package io.github.lucasoliveira28.medicalappointmentapi.controllers;

import io.github.lucasoliveira28.medicalappointmentapi.entities.Doctor;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorRepository;
import io.github.lucasoliveira28.medicalappointmentapi.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    public DoctorRepository doctorRepository;

    @Autowired
    public DoctorService service;

    @GetMapping
    public ResponseEntity<List<Doctor>> findAllDoctors() {
        List<Doctor> list = service.findAllDoctors();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/searchid")
    public ResponseEntity<Doctor> findDoctorById(@RequestParam String id) {
        UUID doctorId = UUID.fromString(id);
        return ResponseEntity.ok(service.findDoctorById(doctorId));
    }

    @GetMapping("/searchname")
    public ResponseEntity<Doctor> findDoctorByName(@RequestParam String name) {
        return ResponseEntity.ok(service.findDoctorByName(name));
    }

    @GetMapping("/searchcrm")
    public ResponseEntity<Doctor> findDoctorByCrm(@RequestParam String crm) {
        return ResponseEntity.ok(service.findDoctorByCrm(crm));
    }

    @PostMapping
    public ResponseEntity<Doctor> saveDoctor(@RequestBody Doctor doctor) {
        service.CreateDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(doctor);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable String id, @RequestBody Doctor doctor) {
        return ResponseEntity.ok().body(service.updateActive(UUID.fromString(id), doctor));
    }


}
