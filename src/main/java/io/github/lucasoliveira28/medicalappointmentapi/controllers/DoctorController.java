package io.github.lucasoliveira28.medicalappointmentapi.controllers;

import io.github.lucasoliveira28.medicalappointmentapi.dto.requests.DoctorRequestDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.requests.update.DoctorUpdateRequestDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.responses.DoctorResponseDTO;
import io.github.lucasoliveira28.medicalappointmentapi.services.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    public final DoctorService service;

    @Autowired
    public DoctorController(DoctorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponseDTO>> getAllDoctors() {
        return ResponseEntity.ok(service.getAllDoctors());
    }

    @GetMapping("/search")
    public ResponseEntity<DoctorResponseDTO> getDoctor(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(service.getDoctor(params));
    }

    @PostMapping("/new")
    public ResponseEntity<DoctorRequestDTO> saveDoctor(@RequestBody @Valid DoctorRequestDTO doctor) {
        service.saveDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(doctor);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DoctorResponseDTO> deleteDoctor(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.deleteDoctor(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DoctorResponseDTO> updateDoctor(@PathVariable Long id, @RequestBody DoctorUpdateRequestDTO doctor) {
        return ResponseEntity.ok(service.updateDoctor(id, doctor));
    }
}
