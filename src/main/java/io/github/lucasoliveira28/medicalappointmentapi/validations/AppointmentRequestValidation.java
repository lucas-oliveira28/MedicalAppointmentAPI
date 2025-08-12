package io.github.lucasoliveira28.medicalappointmentapi.validations;


import io.github.lucasoliveira28.medicalappointmentapi.exceptions.RequestErrorException;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorAvailabilityRepository;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorRepository;
import io.github.lucasoliveira28.medicalappointmentapi.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppointmentRequestValidation {


    private final DoctorAvailabilityRepository doctorAvailabilityRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public AppointmentRequestValidation(
            DoctorAvailabilityRepository doctorAvailabilityRepository,
            DoctorRepository doctorRepository,
            PatientRepository patientRepository
    ) {
        this.doctorAvailabilityRepository = doctorAvailabilityRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public void doctorIdValidation(Long id) {
        if (doctorRepository.findById(id).isPresent()) {
            return;
        }
        throw new RequestErrorException("Doctor Id not found");
    }

    public void patientIdValidation(Long id) {
        if (patientRepository.findById(id).isPresent()) {
            return;
        }
        throw new RequestErrorException("Patient Id not found");
    }

    public void doctorAvailabilityIdValidation(Long id) {
        if (doctorAvailabilityRepository.findById(id).isPresent()) {
            return;
        }
        throw new RequestErrorException("Appointment Id not found");
    }

}
