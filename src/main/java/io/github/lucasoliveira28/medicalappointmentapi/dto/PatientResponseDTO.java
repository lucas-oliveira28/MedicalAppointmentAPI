package io.github.lucasoliveira28.medicalappointmentapi.dto;

public record PatientResponseDTO(
        String name,
        String email,
        String phone,
        String cpf,
        Boolean active
) {

}
