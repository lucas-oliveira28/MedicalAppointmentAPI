package io.github.lucasoliveira28.medicalappointmentapi.repository;

import io.github.lucasoliveira28.medicalappointmentapi.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    public Patient findPatientById(Long id);

    public Patient findPatientByName(String name);

    public Patient findPatientByCpf(String cpf);

    public Boolean existsByEmail(String email);

    public Boolean existsByPhone(String phone);

    public Boolean existsByCpf(String cpf);
}
