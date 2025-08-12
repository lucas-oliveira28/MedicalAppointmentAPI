package io.github.lucasoliveira28.medicalappointmentapi.repository;

import io.github.lucasoliveira28.medicalappointmentapi.entities.DoctorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {

    DoctorAvailability findDoctorAvailabilityById(Long id);

    List<DoctorAvailability> findAllByAvailable(boolean b);
}
