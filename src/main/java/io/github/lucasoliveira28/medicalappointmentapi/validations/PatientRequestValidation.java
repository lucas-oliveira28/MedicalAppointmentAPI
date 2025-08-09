package io.github.lucasoliveira28.medicalappointmentapi.validations;

import io.github.lucasoliveira28.medicalappointmentapi.entities.enums.MedicalSpecialty;
import io.github.lucasoliveira28.medicalappointmentapi.exceptions.RequestErrorException;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorRepository;
import io.github.lucasoliveira28.medicalappointmentapi.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientRequestValidation {

    @Autowired
    private PatientRepository patientRepository;

    public Boolean isNameValid(String name) {
        if (name != null
        && !name.isBlank()
        && name.matches("[a-zA-Z]+")
        && name.length() > 2
        && name.length() <= 25) {
            return true;
        }
        throw new RequestErrorException("Name is invalid");
    }

    public Boolean isEmailValid(String email) {
        if (email != null
        && !email.isBlank()
        && isEmailUnique(email)
        && email.contains("@")
        && email.contains(".com")) {
            return true;
        }
        throw new RequestErrorException("Email is invalid");
    }

    public Boolean isPhoneValid(String phone) {
        String regex = "^(\\(?\\d{2}\\)?\\s?)?(9\\d{4}-?\\d{4})$";

        if (phone != null && phone.matches(regex)) {
            String normalizedPhone = normalizePhone(phone);
            if (isPhoneUnique(normalizedPhone)) {
                return true;
            }
        }
        throw new RequestErrorException("Phone is invalid");
    }

    public Boolean isCpfValid(String cpf) {
        if (cpf != null
        && !cpf.isBlank()
        && isCpfUnique(cpf)
        && cpf.matches("^[0-9]+$")
        && cpf.length() == 11) {
            return true;
        }
        throw new RequestErrorException("CPF is invalid");
    }

    public Boolean isPasswordValid(String password) {
        if (password != null
        && !password.isBlank()
        && password.length() >= 8) {
            return true;
        }
        throw new RequestErrorException("Password is invalid");
    }

    public Boolean isActiveValid(String active) {
        if (active != null) {
            if (active.equals("true") || active.equals("false")) {
                return true;
            }
        }
        throw new RequestErrorException("Active status is invalid");
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

    public String normalizePhone(String phone) {

        String digits = phone.replaceAll("\\D", "");

        if (digits.startsWith("0")) {
            digits = digits.substring(1);
        }

        if (digits.startsWith("55") && digits.length() == 13) {
            return "+" + digits;
        }

        if (digits.length() == 11) {
            return "+55" + digits;
        }

        throw new RequestErrorException("Invalid phone number");
    }

}
