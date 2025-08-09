package io.github.lucasoliveira28.medicalappointmentapi.validations;

import io.github.lucasoliveira28.medicalappointmentapi.entities.enums.MedicalSpecialty;
import io.github.lucasoliveira28.medicalappointmentapi.exceptions.RequestErrorException;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorRepository;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorRequestValidation {

    @Autowired
    private DoctorRepository doctorRepository;

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

    public Boolean isMedicalSpecialtyValid(String medicalSpecialty) {
        if (medicalSpecialty != null ) {
            try {
                MedicalSpecialty.valueOf(medicalSpecialty.toUpperCase());
                return true;
            } catch (IllegalArgumentException e) {
                throw new RequestErrorException("Medical special is invalid");
            }
        }
        throw new RequestErrorException("Medical special is invalid");
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

    public Boolean isCrmValid(String crm) {
        if (crm != null
                && !crm.isBlank()
                && isCrmUnique(crm)
                && crm.matches("^[0-9]{5}-[A-Z]{2}$")) {
            return true;
        }
        throw new RequestErrorException("CRM is invalid");
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
