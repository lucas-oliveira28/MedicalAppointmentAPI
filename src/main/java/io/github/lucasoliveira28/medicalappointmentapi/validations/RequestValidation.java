package io.github.lucasoliveira28.medicalappointmentapi.validations;

import io.github.lucasoliveira28.medicalappointmentapi.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestValidation {

    @Autowired
    private PatientRepository patientRepository;

    public Boolean isNameValid(String name) {
        return name.length() > 2 && name.length() <= 25;
    }

    public Boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".com");
    }

    public Boolean isEmailUnique(String email) {
        return !patientRepository.existsByEmail(email);
    }

    public Boolean isPhoneUnique(String phone) {
        String normalized = normalizePhone(phone);
        return normalized != null && !patientRepository.existsByPhone(normalized);
    }

    public Boolean isCpfUnique(String cpf) {
        return !patientRepository.existsByCpf(cpf);
    }

    public Boolean isPhoneValid(String phone) {
        // Aceita formatos com ou sem parênteses e hifens
        String regex = "^(\\(?\\d{2}\\)?\\s?)?(9\\d{4}-?\\d{4})$";
        return phone != null && phone.matches(regex);
    }

    public String normalizePhone(String phone) {
        if (phone == null) return null;

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

        return null;
    }


}
