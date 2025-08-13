package io.github.lucasoliveira28.medicalappointmentapi.dto.responses;

import io.github.lucasoliveira28.medicalappointmentapi.entities.enums.PatientGender;

import java.time.LocalDate;

public record PatientResponseDTO(

        Long id,
        String name,
        String email,
        String phone,
        String cpf,
        String address,
        LocalDate birthDate,
        PatientGender gender,
        String medicalHistory,
        Boolean active

) {

}
