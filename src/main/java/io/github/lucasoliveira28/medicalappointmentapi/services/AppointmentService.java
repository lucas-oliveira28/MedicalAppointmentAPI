package io.github.lucasoliveira28.medicalappointmentapi.services;

import io.github.lucasoliveira28.medicalappointmentapi.dto.requests.AppointmentRequestDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.responses.AppointmentResponseDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.responses.DoctorResponseDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.responses.PatientResponseDTO;
import io.github.lucasoliveira28.medicalappointmentapi.entities.Appointment;
import io.github.lucasoliveira28.medicalappointmentapi.entities.Doctor;
import io.github.lucasoliveira28.medicalappointmentapi.entities.Patient;
import io.github.lucasoliveira28.medicalappointmentapi.entities.enums.AppointmentStatus;
import io.github.lucasoliveira28.medicalappointmentapi.repository.AppointmentRepository;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorAvailabilityRepository;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorRepository;
import io.github.lucasoliveira28.medicalappointmentapi.repository.PatientRepository;
import io.github.lucasoliveira28.medicalappointmentapi.validations.AppointmentRequestValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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

    private AppointmentResponseDTO buildAppointmentResponseDTO(Appointment appointment) {
        return new AppointmentResponseDTO(
                appointment.getId(), appointment.getDate(), appointment.getReason(),
                appointment.getStatus(), buildPatientResponseDTO(appointment.getPatient()),
                buildDoctorResponseDTO(appointment.getDoctor())
        );
    }

    private PatientResponseDTO buildPatientResponseDTO(Patient patient) {
        return new PatientResponseDTO(
                patient.getId(), patient.getName(), patient.getEmail(),
                patient.getPhone(), patient.getCpf(), patient.getActive()
        );
    }

    private DoctorResponseDTO buildDoctorResponseDTO(Doctor doctor) {
        return new DoctorResponseDTO(
                doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getPhone(),
                doctor.getCrm(), doctor.getSpecialty(), doctor.getActive()
        );
    }

    public Appointment buildAppointmentEntity(AppointmentRequestDTO dto) {
        return new Appointment(
                dto.date(), dto.reason(), AppointmentStatus.valueOf(dto.status()),
                patientRepository.findPatientById(dto.patientId()),
                doctorRepository.findDoctorById(dto.doctorId()),
                doctorAvailabilityRepository.findByDoctorId(dto.availabilityId())
        );
    }

    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::buildAppointmentResponseDTO)
                .collect(Collectors.toList());
    }

    public void saveAppointment(AppointmentRequestDTO dto) {
        validation.doctorIdValidation(dto.doctorId());
        validation.patientIdValidation(dto.patientId());
        validation.doctorAvailabilityIdValidation(dto.availabilityId());
        var appointment = buildAppointmentEntity(dto);
        appointmentRepository.save(appointment);
    }

}


