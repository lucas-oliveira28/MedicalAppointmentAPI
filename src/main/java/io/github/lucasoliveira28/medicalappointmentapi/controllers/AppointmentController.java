package io.github.lucasoliveira28.medicalappointmentapi.controllers;

import io.github.lucasoliveira28.medicalappointmentapi.entities.Appointment;
import io.github.lucasoliveira28.medicalappointmentapi.repository.AppointmentRepository;
import io.github.lucasoliveira28.medicalappointmentapi.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    public AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService service;

    @GetMapping
    public ResponseEntity<List<Appointment>> findAllAppointments() {
        List<Appointment> list = service.findAllAppointments();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/searchid")
    public ResponseEntity<Appointment> findAppointmentById(@RequestParam Long id) {
        return ResponseEntity.ok(service.findAppointmentById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        return ResponseEntity.ok(service.updateStatus(id, appointment));
    }
}
