package io.github.lucasoliveira28.medicalappointmentapi.validations;

import io.github.lucasoliveira28.medicalappointmentapi.exceptions.RequestErrorException;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorRequestValidation {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorRequestValidation(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public void isEmailUnique(String email) {
        if (!doctorRepository.existsByEmail(email)) {
            return;
        }
        throw new RequestErrorException("Email already exists");
    }

    public void isPhoneUnique(String normalizedPhone) {
        if (!doctorRepository.existsByPhone(normalizedPhone)) {
            return;
        }
        throw new RequestErrorException("Phone already exists");
    }

    public void isCrmUnique(String crm) {
        if (!doctorRepository.existsByCrm(crm)) {
            return;
        }
        throw new RequestErrorException("CRM already exists");
    }
}
