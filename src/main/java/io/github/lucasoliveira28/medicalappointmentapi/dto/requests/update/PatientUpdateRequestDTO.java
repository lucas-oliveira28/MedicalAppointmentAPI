package io.github.lucasoliveira28.medicalappointmentapi.dto.requests.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record PatientUpdateRequestDTO(

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

        @NotEmpty(message = "CPF cannot be empty")
        @CPF(message = "CPF invalid")
        String cpf,

        @NotEmpty(message = "address cannot be empty")
        String address,

        @NotEmpty(message = "birthDate cannot be empty")
        @Past(message = "birthDate invalid")
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate birthDate,

        @NotEmpty(message = "gender cannot be empty")
        String gender,

        @NotEmpty(message = "Password cannot be empty")
        @Size(min = 8, max = 20, message = "Password must contain between 8 and 20 characters")
        String password,

        @NotEmpty(message = "medicalHistory cannot be empty")
        String medicalHistory,

        @NotEmpty(message = "Active cannot be empty")
        Boolean active

) {

}
