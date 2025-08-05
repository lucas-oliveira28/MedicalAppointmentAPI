package io.github.lucasoliveira28.medicalappointmentapi.controllers;

import io.github.lucasoliveira28.medicalappointmentapi.entities.Patient;
import io.github.lucasoliveira28.medicalappointmentapi.repository.PatientRepository;
import io.github.lucasoliveira28.medicalappointmentapi.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    public PatientRepository patientRepository;

    @Autowired
    public PatientService service;

    @GetMapping
    public ResponseEntity<List<Patient>> findAllPatients() {
        List<Patient> list = service.findAllPatients();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/searchid")
    public ResponseEntity<Patient> findPatientById(@RequestParam Long id) {
        return ResponseEntity.ok(service.findPatientById(id));
    }

    @GetMapping("/searchname")
    public ResponseEntity<Patient> findAllPatientsByName(@RequestParam String name) {
        return ResponseEntity.ok(service.findPatientByName(name));
    }

    @GetMapping("/searchcpf")
    public ResponseEntity<Patient> findPatientByCpf(@RequestParam String cpf) {
        return ResponseEntity.ok(service.findPatientByCpf(cpf));
    }

    @PostMapping
    public ResponseEntity<Patient> savePatient(@RequestBody Patient patient) {
        service.CreatePatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        return ResponseEntity.ok().body(service.updateActive(id, patient));
    }
}
