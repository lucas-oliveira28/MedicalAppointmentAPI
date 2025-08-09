package io.github.lucasoliveira28.medicalappointmentapi.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DoctorRequestDTO(

        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email")
        String email,

        @NotBlank(message = "Phone cannot be blank")
        String phone,

        @NotBlank(message = "CRM cannot be blank")
        String crm,

        @NotBlank(message = "Password cannot be blank")
        String password,

        @NotBlank(message = "Password cannot be blank")
        String specialty

) {

}
