package io.github.lucasoliveira28.medicalappointmentapi.dto.responses;

public record PatientResponseDTO(

        Long id,
        String name,
        String email,
        String phone,
        String cpf,
        Boolean active

) {

}
