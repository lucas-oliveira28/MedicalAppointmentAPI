package io.github.lucasoliveira28.medicalappointmentapi.validations;

import io.github.lucasoliveira28.medicalappointmentapi.entities.enums.MedicalSpecialty;
import io.github.lucasoliveira28.medicalappointmentapi.exceptions.RequestErrorException;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorRequestValidation {

    private final DoctorRepository doctorRepository;
    private final GeneralValidation validation;

    @Autowired
    public DoctorRequestValidation(DoctorRepository doctorRepository, GeneralValidation validation) {
        this.doctorRepository = doctorRepository;
        this.validation = validation;
    }

    public void isMedicalSpecialtyValid(String medicalSpecialty) {
        if (medicalSpecialty != null ) {
            try {
                MedicalSpecialty.valueOf(medicalSpecialty.toUpperCase());
                return;
            } catch (IllegalArgumentException e) {
                throw new RequestErrorException("Medical special is invalid");
            }
        }
        throw new RequestErrorException("Medical special is invalid");
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

    public void isCrmValid(String crm) {
        if (crm != null
                && !crm.isBlank()
                && isCrmUnique(crm)
                && crm.matches("^[0-9]{5}-[A-Z]{2}$")) {
            return;
        }
        throw new RequestErrorException("CRM is invalid");
    }


    public Boolean isEmailUnique(String email) {
        if (!doctorRepository.existsByEmail(email)) {
            return true;
        }
        throw new RequestErrorException("Email already exists");
    }

    public Boolean isPhoneUnique(String normalizedPhone) {
        if (!doctorRepository.existsByPhone(normalizedPhone)) {
            return true;
        }
        throw new RequestErrorException("Phone already exists");
    }

    public Boolean isCrmUnique(String crm) {
        if (!doctorRepository.existsByCrm(crm)) {
            return true;
        }
        throw new RequestErrorException("CRM already exists");
    }
}
