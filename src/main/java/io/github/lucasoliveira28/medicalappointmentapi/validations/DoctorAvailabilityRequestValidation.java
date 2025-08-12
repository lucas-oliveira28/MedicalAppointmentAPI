package io.github.lucasoliveira28.medicalappointmentapi.validations;


import io.github.lucasoliveira28.medicalappointmentapi.entities.Doctor;
import io.github.lucasoliveira28.medicalappointmentapi.entities.DoctorAvailability;
import io.github.lucasoliveira28.medicalappointmentapi.exceptions.RequestErrorException;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DoctorAvailabilityRequestValidation {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorAvailabilityRequestValidation(
            DoctorRepository doctorRepository
    ) {
        this.doctorRepository = doctorRepository;
    }

    public void availabilityValidation(Long doctorId, LocalDateTime startTime, LocalDateTime endTime) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RequestErrorException("Doctor not found!"));

        for (DoctorAvailability availability : doctor.getAvailabilities()) {
            boolean noOverlap = endTime.isBefore(availability.getStartTime()) ||
                    startTime.isAfter(availability.getEndTime());

            if (!noOverlap) {
                throw new RequestErrorException("Doctor not available at this time!");
            }
        }
    }

}
