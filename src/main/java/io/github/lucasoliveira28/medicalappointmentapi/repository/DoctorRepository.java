package io.github.lucasoliveira28.medicalappointmentapi.repository;

import io.github.lucasoliveira28.medicalappointmentapi.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    public Doctor findDoctorById(Long id);

    public Doctor findDoctorByName(String name);

    public Doctor findDoctorByCrm(String crm);

    public Boolean existsByEmail(String email);

    public Boolean existsByPhone(String phone);

    public Boolean existsByCrm(String crm);
}
