package io.github.lucasoliveira28.medicalappointmentapi.validations;

import io.github.lucasoliveira28.medicalappointmentapi.exceptions.RequestErrorException;
import io.github.lucasoliveira28.medicalappointmentapi.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientRequestValidation {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientRequestValidation(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void isEmailUnique(String email) {
        if (!patientRepository.existsByEmail(email)) {
            return;
        }
        throw new RequestErrorException("Email already exists");
    }

    public void isPhoneUnique(String normalizedPhone) {
        if (!patientRepository.existsByPhone(normalizedPhone)) {
            return;
        }
        throw new RequestErrorException("Phone already exists");
    }

    public void isCpfUnique(String cpf) {
        if (!patientRepository.existsByCpf(cpf)) {
            return;
        }
        throw new RequestErrorException("CPF already exists");
    }
}
