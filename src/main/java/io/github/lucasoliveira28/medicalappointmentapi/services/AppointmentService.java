package io.github.lucasoliveira28.medicalappointmentapi.services;

import io.github.lucasoliveira28.medicalappointmentapi.dto.requests.AppointmentRequestDTO;
import io.github.lucasoliveira28.medicalappointmentapi.entities.Appointment;
import io.github.lucasoliveira28.medicalappointmentapi.entities.enums.AppointmentStatus;
import io.github.lucasoliveira28.medicalappointmentapi.repository.AppointmentRepository;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorAvailabilityRepository;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorRepository;
import io.github.lucasoliveira28.medicalappointmentapi.repository.PatientRepository;
import io.github.lucasoliveira28.medicalappointmentapi.validations.AppointmentRequestValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRequestValidation validation;

    @Autowired
    public AppointmentService(
            AppointmentRepository appointmentRepository,
            DoctorAvailabilityRepository doctorAvailabilityRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            AppointmentRequestValidation validation
    ) {
        this.appointmentRepository = appointmentRepository;
        this.doctorAvailabilityRepository = doctorAvailabilityRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.validation = validation;
    }

    public Appointment buildAppointent(AppointmentRequestDTO dto) {
        validation.isDateValid(dto.date());
        validation.isReasonValid(dto.reason());
        validation.isStatusValid(dto.status());
        validation.isPatientIdValid(dto.patientId());
        validation.isDoctorIdValid(dto.doctorId());
        validation.isAvailabilityIdValid(dto.availabilityId());

        return new Appointment(
                dto.date(), dto.reason(), AppointmentStatus.valueOf(dto.status()),
                patientRepository.findPatientById(dto.patientId()),
                doctorRepository.findDoctorById(dto.doctorId()),
                doctorAvailabilityRepository.findByDoctorId(dto.availabilityId())
        );
    }

}


