package io.github.lucasoliveira28.medicalappointmentapi.exceptions;

public class RequestErrorException extends RuntimeException {
    public RequestErrorException(String message) {
        super(message);
    }
}
