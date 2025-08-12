package io.github.lucasoliveira28.medicalappointmentapi.dto.requests.update;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DoctorUpdateRequestDTO(

        @NotEmpty(message = "Name cannot be empty")
        @Size(min = 3, max = 40, message = "Name must contain between 3 and 40 letters")
        @Pattern(
                regexp = "^(?:[A-ZÀ-Ö][a-zà-öø-ÿ]+|[a-zà-öø-ÿ]{1,3})(?: (?:[A-ZÀ-Ö][a-zà-öø-ÿ]+|[a-zà-öø-ÿ]{1,3}))*$",
                message = "Name must contain only letters, with each main word capitalized"
        )
        String name,

        @NotEmpty(message = "Email cannot be empty")
        @Email(message = "Email invalid")
        String email,

        @NotEmpty(message = "Phone cannot be empty")
        @Pattern(regexp = "^(?:\\(\\d{2}\\)|\\d{2})\\s?9\\d{4}-?\\d{4}$", message = "Phone invalid")
        String phone,

        @NotEmpty(message = "CRM cannot be empty")
        @Pattern(regexp = "^[0-9]{5}-[A-Z]{2}$", message = "CRM invalid")
        String crm,

        @NotEmpty(message = "Password cannot be empty")
        @Size(min = 8, max = 20, message = "Password must contain between 8 and 20 characters")
        String password,

        @NotEmpty(message = "Specialty cannot be empty")
        @Size(min = 3, max = 40, message = "Specialty must contain between 8 and 20 characters")
        @Pattern(
                regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+( [A-Za-zÀ-ÖØ-öø-ÿ]+)*$",
                message = "Specialty must contain only letters and single spaces between words"
        )
        String specialty,

        @NotEmpty(message = "Active cannot be empty")
        Boolean active
) {
}
