package io.github.lucasoliveira28.medicalappointmentapi.dto.requests.update;

public record DoctorUpdateRequestDTO(

        String name,

        String email,

        String phone,

        String crm,

        String password,

        String specialty,

        Boolean active
) {
}
