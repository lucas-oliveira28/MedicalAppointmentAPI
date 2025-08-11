package io.github.lucasoliveira28.medicalappointmentapi.dto.requests.update;

public record AppointmentUpdateRequestDTO(

        String date,

        String reason,

        String status,

        Long patientId,

        Long doctorId,

        Long availabilityId

) {

}
