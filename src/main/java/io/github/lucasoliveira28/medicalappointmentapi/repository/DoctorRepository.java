package io.github.lucasoliveira28.medicalappointmentapi.repository;

import io.github.lucasoliveira28.medicalappointmentapi.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    public Doctor findDoctorById(UUID id);
    public Doctor findDoctorByName(String name);
    public Doctor findDoctorByCrm(String crm);
}
