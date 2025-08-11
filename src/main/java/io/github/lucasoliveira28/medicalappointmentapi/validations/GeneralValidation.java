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

    public void isNameValid(String name) {
        if (name != null
                && !name.isBlank()
                && name.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+( [A-Za-zÀ-ÖØ-öø-ÿ]+)*$")
                && name.length() > 2
                && name.length() <= 25) {
            return;
        }
        throw new RequestErrorException("Name is invalid");
    }

    public void isPasswordValid(String password) {
        if (password != null
                && !password.isBlank()
                && password.length() >= 8) {
            return;
        }
        throw new RequestErrorException("Password is invalid");
    }

    public void isActiveValid(String active) {
        if (active != null) {
            if (active.equals("true") || active.equals("false")) {
                return;
            }
        }
        throw new RequestErrorException("Active status is invalid");
    }
}
