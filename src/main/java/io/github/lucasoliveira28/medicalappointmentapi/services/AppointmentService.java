package io.github.lucasoliveira28.medicalappointmentapi.services;

import io.github.lucasoliveira28.medicalappointmentapi.dto.requests.AppointmentRequestDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.requests.update.AppointmentUpdateRequestDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.responses.AppointmentResponseDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.responses.DoctorAvailabilityResponseDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.responses.DoctorResponseDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.responses.PatientResponseDTO;
import io.github.lucasoliveira28.medicalappointmentapi.entities.Appointment;
import io.github.lucasoliveira28.medicalappointmentapi.entities.Doctor;
import io.github.lucasoliveira28.medicalappointmentapi.entities.DoctorAvailability;
import io.github.lucasoliveira28.medicalappointmentapi.entities.Patient;
import io.github.lucasoliveira28.medicalappointmentapi.entities.enums.AppointmentStatus;
import io.github.lucasoliveira28.medicalappointmentapi.exceptions.RequestNotFoundException;
import io.github.lucasoliveira28.medicalappointmentapi.repository.AppointmentRepository;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorAvailabilityRepository;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorRepository;
import io.github.lucasoliveira28.medicalappointmentapi.repository.PatientRepository;
import io.github.lucasoliveira28.medicalappointmentapi.validations.AppointmentRequestValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
                appointment.getId(),
                appointment.getDate(),
                appointment.getReason(),
                appointment.getStatus(),
                buildPatientResponseDTO(appointment.getPatient()),
                buildDoctorResponseDTO(appointment.getDoctor()),
                buildDoctorAvailabilityResponseDTO(appointment.getAvailability())
        );
    }

    private DoctorAvailabilityResponseDTO buildDoctorAvailabilityResponseDTO(DoctorAvailability doctorAvailability) {
        return new DoctorAvailabilityResponseDTO(
                doctorAvailability.getId(),
                buildDoctorResponseDTO(doctorAvailability.getDoctor()),
                doctorAvailability.getStartTime(),
                doctorAvailability.getEndTime(),
                doctorAvailability.getAvailable()
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

    private Appointment buildAppointmentEntity(AppointmentRequestDTO dto) {
        return new Appointment(
                dto.date(), dto.reason(), AppointmentStatus.valueOf(dto.status()),
                patientRepository.findPatientById(dto.patientId()),
                doctorRepository.findDoctorById(dto.doctorId()),
                doctorAvailabilityRepository.findDoctorAvailabilityById(dto.availabilityId())
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
        doctorAvailabilityRepository.findDoctorAvailabilityById(dto.availabilityId()).setAvailable(false);
        var appointment = buildAppointmentEntity(dto);
        appointmentRepository.save(appointment);
    }

    public AppointmentResponseDTO deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findAppointmentById(id);

        if (appointment != null) {
            appointment.getAvailability().setAvailable(true);
            appointment.getAvailability().setAppointment(null);
            appointmentRepository.delete(appointment);
            return buildAppointmentResponseDTO(appointment);
        }

        throw new RequestNotFoundException("Appointment not found");
    }

    public AppointmentResponseDTO getAppointment(Map<String, String> params) {

        if (params.containsKey("id")) {
            var id = params.get("id");
            Appointment appointment = appointmentRepository.findAppointmentById(Long.parseLong(id));
            if (appointment != null) {
                return buildAppointmentResponseDTO(appointment);
            }
        }
        if (params.containsKey("doctorId")) {
            var doctorId = params.get("doctorId");
            Appointment appointment = appointmentRepository.findAppointmentByDoctorId(Long.parseLong(doctorId));
            if (appointment != null) {
                return buildAppointmentResponseDTO(appointment);
            }
        }
        if (params.containsKey("patientId")) {
            var patientId = params.get("patientId");
            Appointment appointment = appointmentRepository.findAppointmentByPatientId(Long.parseLong(patientId));
            if (appointment != null) {
                return buildAppointmentResponseDTO(appointment);
            }
        }

        throw new RequestNotFoundException("Appointment not found");
    }

    public AppointmentResponseDTO updateAppointment(Long id, AppointmentUpdateRequestDTO dto) {
        Appointment appointment = appointmentRepository.findAppointmentById(id);

        if (appointment != null) {
            if (dto.reason() != null) {
                appointment.setReason(dto.reason());
            }
            if (dto.status() != null) {
                appointment.setStatus(AppointmentStatus.valueOf(dto.status()));
            }
            appointmentRepository.save(appointment);
            return buildAppointmentResponseDTO(appointment);
        }

        throw new RequestNotFoundException("Appointment not found");
    }

}


