package io.github.lucasoliveira28.medicalappointmentapi.dto.responses;

import io.github.lucasoliveira28.medicalappointmentapi.entities.Doctor;
import io.github.lucasoliveira28.medicalappointmentapi.entities.Patient;
import io.github.lucasoliveira28.medicalappointmentapi.entities.enums.AppointmentStatus;

public record AppointmentResponseDTO(

        Long id,

        String date,

        String reason,

        AppointmentStatus status,

        Patient patient,

        Doctor doctor

) {

}
