package io.github.lucasoliveira28.medicalappointmentapi.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.lucasoliveira28.medicalappointmentapi.entities.enums.AppointmentStatus;

import java.time.LocalDateTime;

public record AppointmentResponseDTO(

        Long id,

        @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm")
        LocalDateTime date,

        String reason,

        AppointmentStatus status,

        PatientResponseDTO patient,

        DoctorResponseDTO doctor,

        DoctorAvailabilityResponseDTO availability

) {

}
