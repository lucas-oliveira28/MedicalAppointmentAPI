package io.github.lucasoliveira28.medicalappointmentapi.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DoctorRequestDTO(

        @NotBlank(message = "Name cannot be blank")
        @Size(min = 3, max = 40, message = "Name must contain between 3 and 40 letters")
        @Pattern(
                regexp = "^(?:[A-ZÀ-Ö][a-zà-öø-ÿ]+|[a-zà-öø-ÿ]{1,3})(?: (?:[A-ZÀ-Ö][a-zà-öø-ÿ]+|[a-zà-öø-ÿ]{1,3}))*$",
                message = "Name must contain only letters, with each main word capitalized"
        )
        String name,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email")
        String email,

        @NotBlank(message = "Phone cannot be blank")
        @Pattern(regexp = "^(?:\\(\\d{2}\\)|\\d{2})\\s?9\\d{4}-?\\d{4}$", message = "Phone invalid")
        String phone,

        @NotBlank(message = "CRM cannot be blank")
        @Pattern(regexp = "^[0-9]{5}-[A-Z]{2}$", message = "CRM invalid")
        String crm,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, max = 20, message = "Password must contain between 8 and 20 characters")
        String password,

        @NotBlank(message = "Specialty cannot be blank")
        @Size(min = 3, max = 40, message = "Specialty must contain between 8 and 20 characters")
        @Pattern(
                regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+( [A-Za-zÀ-ÖØ-öø-ÿ]+)*$",
                message = "Specialty must contain only letters and single spaces between words"
        )
        String specialty

) {

}
