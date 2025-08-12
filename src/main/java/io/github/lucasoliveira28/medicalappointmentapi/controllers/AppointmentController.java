package io.github.lucasoliveira28.medicalappointmentapi.controllers;

import io.github.lucasoliveira28.medicalappointmentapi.dto.requests.AppointmentRequestDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.responses.AppointmentResponseDTO;
import io.github.lucasoliveira28.medicalappointmentapi.repository.AppointmentRepository;
import io.github.lucasoliveira28.medicalappointmentapi.services.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    public final AppointmentRepository appointmentRepository;
    public final AppointmentService service;

    @Autowired
    public AppointmentController(AppointmentRepository appointmentRepository, AppointmentService service) {
        this.appointmentRepository = appointmentRepository;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointments() {
        return ResponseEntity.ok(service.getAllAppointments());
    }

    @PostMapping("/new")
    public ResponseEntity<AppointmentRequestDTO> saveAppointment(@RequestBody @Valid AppointmentRequestDTO appointment) {
        service.saveAppointment(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointment);
    }

}
