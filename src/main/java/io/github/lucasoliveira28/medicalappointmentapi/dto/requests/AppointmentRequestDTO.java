package io.github.lucasoliveira28.medicalappointmentapi.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentRequestDTO(

        @NotNull(message = "Date is required")
        @Future(message = "The date must be in the future")
        @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm")
        LocalDateTime date,

        @NotBlank(message = "Reason cannot be blank")
        String reason,

        @NotBlank(message = "Status cannot be blank")
        String status,

        @NotNull(message = "PatientID cannot be null")
        Long patientId,

        @NotNull(message = "DoctorID cannot be null")
        Long doctorId,

        @NotNull(message = "AvailabilityID cannot be null")
        Long availabilityId

) {

}
