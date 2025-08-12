package io.github.lucasoliveira28.medicalappointmentapi.dto.requests.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public record DoctorAvailabilityUpdateRequestDTO(

        @NotEmpty(message = "DoctorID cannot be empty")
        Long doctorID,

        @NotEmpty(message = "Date cannot be empty")
        @Future(message = "Date must be in future")
        @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm")
        LocalDateTime startDate,

        @NotEmpty(message = "Date cannot be empty")
        @Future(message = "Date must be in future")
        @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm")
        LocalDateTime endDate,

        @NotEmpty(message = "Available cannot be empty")
        Boolean available
) {

}
