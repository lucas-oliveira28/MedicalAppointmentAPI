package io.github.lucasoliveira28.medicalappointmentapi.exceptions;

public class RequestNotFoundException extends RuntimeException {
    public RequestNotFoundException(String message) {
        super(message);
    }
}
