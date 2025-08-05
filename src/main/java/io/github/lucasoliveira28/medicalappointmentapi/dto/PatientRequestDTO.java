package io.github.lucasoliveira28.medicalappointmentapi.dto;

public record PatientRequestDTO(
        Long id,
        String name,
        String email,
        String phone,
        String cpf,
        Boolean active
) {

}
