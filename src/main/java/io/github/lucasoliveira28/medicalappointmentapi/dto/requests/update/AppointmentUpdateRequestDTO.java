package io.github.lucasoliveira28.medicalappointmentapi.dto.requests.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record AppointmentUpdateRequestDTO(

        @NotEmpty(message = "Date cannot be empty")
        @Future(message = "Date must be in future")
        @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm")
        LocalDateTime date,

        @NotEmpty(message = "Reason cannot be empty")
        String reason,

        @NotEmpty(message = "Status cannot be empty")
        String status,

        @NotEmpty(message = "PatientID cannot be empty")
        Long patientId,

        @NotEmpty(message = "DoctorID cannot be empty")
        Long doctorId,

        @NotEmpty(message = "AvailabilityID cannot be empty")
        Long availabilityId,

        @NotEmpty(message = "ReasonForCancellation cannot be empty")
        @Size(min = 10, max = 255, message = "ReasonForCancellation must contain between 10 and 255 characters")
        String reasonForCancellation

) {

}
