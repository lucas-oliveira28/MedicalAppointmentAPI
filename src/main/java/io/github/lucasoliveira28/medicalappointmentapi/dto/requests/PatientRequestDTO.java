package io.github.lucasoliveira28.medicalappointmentapi.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record PatientRequestDTO(

        @NotBlank(message = "name cannot be blank")
        @Size(min = 3, max = 40, message = "name must contain between 3 and 40 letters")
        @Pattern(
                regexp = "^(?:[A-ZÀ-Ö][a-zà-öø-ÿ]+|[a-zà-öø-ÿ]{1,3})(?: (?:[A-ZÀ-Ö][a-zà-öø-ÿ]+|[a-zà-öø-ÿ]{1,3}))*$",
                message = "name must contain only letters, with each main word capitalized"
        )
        String name,

        @NotBlank(message = "email cannot be blank")
        @Email(message = "Invalid email")
        String email,

        @NotBlank(message = "phone cannot be blank")
        @Pattern(regexp = "^(?:\\(\\d{2}\\)|\\d{2})\\s?9\\d{4}-?\\d{4}$", message = "Phone invalid")
        String phone,

        @NotBlank(message = "cpf cannot be blank")
        @CPF(message = "cpf invalid")
        String cpf,

        @NotBlank(message = "address cannot be blank")
        String address,

        @NotNull(message = "birthDate cannot be null")
        @Past(message = "birthDate invalid")
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate birthDate,

        @NotBlank(message = "gender cannot be blank")
        String gender,

        @NotBlank(message = "password cannot be blank")
        @Size(min = 8, max = 20, message = "Password must contain between 8 and 20 characters")
        String password,

        @NotEmpty(message = "medicalHistory cannot be empty")
        String medicalHistory

) {

}
