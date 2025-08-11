package io.github.lucasoliveira28.medicalappointmentapi.repository;

import io.github.lucasoliveira28.medicalappointmentapi.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findDoctorById(Long id);

    Doctor findDoctorByName(String name);

    Doctor findDoctorByCrm(String crm);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

    Boolean existsByCrm(String crm);
}
