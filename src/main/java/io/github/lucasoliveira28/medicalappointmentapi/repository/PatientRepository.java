package io.github.lucasoliveira28.medicalappointmentapi.repository;

import io.github.lucasoliveira28.medicalappointmentapi.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findPatientById(Long id);
    Patient findPatientByName(String name);
    Patient findPatientByCpf(String cpf);
    Boolean existsByEmail(String email);
    Boolean existsByPhone(String phone);
    Boolean existsByCpf(String cpf);

}
