package io.github.lucasoliveira28.medicalappointmentapi.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DoctorAvailabilityRequestDTO(

        @NotNull(message = "Doctor ID cannot be null")
        Long doctorId,

        @NotNull(message = "Date is required")
        @Future(message = "The date must be in the future")
        @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm")
        LocalDateTime startDate,

        @NotNull(message = "Date is required")
        @Future(message = "The date must be in the future")
        @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm")
        LocalDateTime endDate

) {

}
