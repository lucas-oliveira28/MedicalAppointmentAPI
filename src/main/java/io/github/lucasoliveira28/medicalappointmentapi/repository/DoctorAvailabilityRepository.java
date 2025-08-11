package io.github.lucasoliveira28.medicalappointmentapi.repository;

import io.github.lucasoliveira28.medicalappointmentapi.entities.DoctorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {

    DoctorAvailability findByDoctorId(Long doctorId);
    
}
