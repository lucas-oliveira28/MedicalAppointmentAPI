package io.github.lucasoliveira28.medicalappointmentapi.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record AppointmentRequestDTO(

        @NotBlank(message = "Date cannot be blank")
        String date,

        @NotBlank(message = "Reason cannot be blank")
        String reason,

        @NotBlank(message = "Status cannot be blank")
        String status,

        @NotBlank(message = "PatientID cannot be blank")
        Long patientId,

        @NotBlank(message = "DoctorID cannot be blank")
        Long doctorId,

        @NotBlank(message = "AvailabilityID cannot be blank")
        Long availabilityId

) {

}
