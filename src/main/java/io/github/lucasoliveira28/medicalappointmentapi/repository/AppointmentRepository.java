package io.github.lucasoliveira28.medicalappointmentapi.repository;

import io.github.lucasoliveira28.medicalappointmentapi.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Appointment findAppointmentById(Long id);
    Appointment findAppointmentByDoctorId(Long doctorId);
    Appointment findAppointmentByPatientId(Long patientId);
    Appointment findAppointmentByAvailabilityId(Long availabilityId);

}
