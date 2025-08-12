package io.github.lucasoliveira28.medicalappointmentapi.dto.responses;

import io.github.lucasoliveira28.medicalappointmentapi.entities.enums.MedicalSpecialty;

public record DoctorResponseDTO(

        Long id,
        String name,
        String email,
        String phone,
        String crm,
        MedicalSpecialty specialty,
        Boolean active

) {

}
