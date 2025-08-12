package io.github.lucasoliveira28.medicalappointmentapi.validations;

import io.github.lucasoliveira28.medicalappointmentapi.exceptions.RequestErrorException;
import org.springframework.stereotype.Component;

@Component
public class GeneralValidation {

    public String normalizePhone(String phone) {

        String digits = phone.replaceAll("\\D", "");

        if (digits.length() == 11) {
            return digits;
        }

        throw new RequestErrorException("Invalid phone number");
    }
}
