package io.github.lucasoliveira28.medicalappointmentapi.exceptions.handlers;

import io.github.lucasoliveira28.medicalappointmentapi.exceptions.RequestErrorException;
import io.github.lucasoliveira28.medicalappointmentapi.exceptions.RequestNotFoundException;
import io.github.lucasoliveira28.medicalappointmentapi.exceptions.body.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.stream.Collectors;

@ControllerAdvice
public class RequestExceptionHandler {

    @ExceptionHandler(RequestNotFoundException.class)
    public ResponseEntity<StandardError> handleRequestNotFoundException(RequestNotFoundException e, HttpServletRequest request) {
        String error = "Request not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(),
                error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(RequestErrorException.class)
    public ResponseEntity<StandardError> handleRequestErrorException(RequestErrorException e, HttpServletRequest request) {
        String error = "Request error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(),
                error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(),
                "Bad request error", message, request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

}
