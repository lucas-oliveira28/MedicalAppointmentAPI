package io.github.lucasoliveira28.medicalappointmentapi.validations;

import io.github.lucasoliveira28.medicalappointmentapi.exceptions.RequestErrorException;
import io.github.lucasoliveira28.medicalappointmentapi.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppointmentRequestValidation {

    private final AppointmentRepository appointmentRepository;
    private final GeneralValidation validation;

    @Autowired
    public AppointmentRequestValidation(AppointmentRepository appointmentRepository, GeneralValidation validation) {
        this.appointmentRepository = appointmentRepository;
        this.validation = validation;
    }

    public void isDateValid(String date) {
        if (!date.matches("^\\d{2}-\\d{2}-\\d{4}T\\d{2}:\\d{2}$")) {
            throw new RequestErrorException("Invalid date format");
        }
    }
}
