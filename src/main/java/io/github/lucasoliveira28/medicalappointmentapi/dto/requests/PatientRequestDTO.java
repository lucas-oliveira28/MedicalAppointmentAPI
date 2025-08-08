package io.github.lucasoliveira28.medicalappointmentapi.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PatientRequestDTO(

        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email")
        String email,

        @NotBlank(message = "Phone cannot be blank")
        String phone,

        @NotBlank(message = "CPF cannot be blank")
        String cpf,

        @NotBlank(message = "Password cannot be blank")
        @NotNull(message = "Password cannot be null")
        String password

) {

}
