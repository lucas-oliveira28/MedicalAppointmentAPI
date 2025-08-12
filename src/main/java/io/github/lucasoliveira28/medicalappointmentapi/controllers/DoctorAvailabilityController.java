package io.github.lucasoliveira28.medicalappointmentapi.controllers;

import io.github.lucasoliveira28.medicalappointmentapi.dto.requests.DoctorAvailabilityRequestDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.responses.DoctorAvailabilityResponseDTO;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorAvailabilityRepository;
import io.github.lucasoliveira28.medicalappointmentapi.services.DoctorAvailabilityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctoravailability")
public class DoctorAvailabilityController {

    public final DoctorAvailabilityRepository doctorAvailabilityRepository;
    public final DoctorAvailabilityService service;

    @Autowired
    public DoctorAvailabilityController(DoctorAvailabilityRepository doctorAvailabilityRepository, DoctorAvailabilityService service) {
        this.doctorAvailabilityRepository = doctorAvailabilityRepository;
        this.service = service;
    }

    @GetMapping("/available")
    public ResponseEntity<List<DoctorAvailabilityResponseDTO>> getAllDoctorAvailable() {
        return ResponseEntity.ok(service.getAllDoctorAvailabilityAvailable());
    }

    @GetMapping("/scheduled")
    public ResponseEntity<List<DoctorAvailabilityResponseDTO>> getAllDoctorAvailabilityScheduled() {
        return ResponseEntity.ok(service.getAllDoctorAvailabilityScheduled());
    }

    @PostMapping("/new")
    public ResponseEntity<DoctorAvailabilityRequestDTO> saveDoctorAvailability(@RequestBody @Valid DoctorAvailabilityRequestDTO doctorAvailability) {
        service.saveDoctorAvailability(doctorAvailability);
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorAvailability);
    }

}
