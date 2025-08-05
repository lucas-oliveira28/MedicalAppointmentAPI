package io.github.lucasoliveira28.medicalappointmentapi.controllers;

import io.github.lucasoliveira28.medicalappointmentapi.dto.PatientResponseDTO;
import io.github.lucasoliveira28.medicalappointmentapi.entities.Patient;
import io.github.lucasoliveira28.medicalappointmentapi.dto.PatientRequestDTO;
import io.github.lucasoliveira28.medicalappointmentapi.repository.PatientRepository;
import io.github.lucasoliveira28.medicalappointmentapi.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    public PatientRepository patientRepository;

    @Autowired
    public PatientService service;

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        return ResponseEntity.ok(service.getAllPatients());
    }

    @GetMapping("/search")
    public ResponseEntity<PatientResponseDTO> getPatient(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(service.getPatient(params));
    }

    @PostMapping
    public ResponseEntity<PatientRequestDTO> savePatient(@RequestBody PatientRequestDTO patient) {
        service.savePatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }
}
