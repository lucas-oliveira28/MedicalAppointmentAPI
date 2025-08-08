package io.github.lucasoliveira28.medicalappointmentapi.dto.requests.update;

public record PatientUpdateRequestDTO(

        String name,

        String email,

        String phone,

        String cpf,

        String password
) {
}
