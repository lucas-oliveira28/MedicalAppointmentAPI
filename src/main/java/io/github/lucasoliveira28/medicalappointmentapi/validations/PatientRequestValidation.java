package io.github.lucasoliveira28.medicalappointmentapi.validations;

import io.github.lucasoliveira28.medicalappointmentapi.exceptions.RequestErrorException;
import io.github.lucasoliveira28.medicalappointmentapi.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientRequestValidation {

    private final PatientRepository patientRepository;
    private final GeneralValidation validation;

    @Autowired
    public PatientRequestValidation(PatientRepository patientRepository, GeneralValidation validation) {
        this.patientRepository = patientRepository;
        this.validation = validation;
    }

    public void isEmailValid(String email) {
        if (email != null
        && !email.isBlank()
        && isEmailUnique(email)
        && email.contains("@")
        && email.contains(".com")) {
            return;
        }
        throw new RequestErrorException("Email is invalid");
    }

    public void isPhoneValid(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new RequestErrorException("Phone is invalid");
        }

        String digits = validation.normalizePhone(phone);

        if (!digits.matches("^[1-9]{2}9\\d{8}$")) {
            throw new RequestErrorException("Phone is invalid");
        }

        if (!isPhoneUnique(digits)) {
            throw new RequestErrorException("Phone already exists");
        }
    }

    public void isCpfValid(String cpf) {
        if (cpf != null
        && !cpf.isBlank()
        && isCpfUnique(cpf)
        && cpf.matches("^[0-9]+$")
        && cpf.length() == 11) {
            return;
        }
        throw new RequestErrorException("CPF is invalid");
    }

    public Boolean isEmailUnique(String email) {
        if (!patientRepository.existsByEmail(email)) {
            return true;
        }
        throw new RequestErrorException("Email already exists");
    }

    public Boolean isPhoneUnique(String normalizedPhone) {
        if (!patientRepository.existsByPhone(normalizedPhone)) {
            return true;
        }
        throw new RequestErrorException("Phone already exists");
    }

    public Boolean isCpfUnique(String cpf) {
        if (!patientRepository.existsByCpf(cpf)) {
            return true;
        }
        throw new RequestErrorException("CPF already exists");
    }
}
