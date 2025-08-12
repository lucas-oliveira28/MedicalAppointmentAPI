package io.github.lucasoliveira28.medicalappointmentapi.services;

import io.github.lucasoliveira28.medicalappointmentapi.dto.requests.DoctorAvailabilityRequestDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.responses.*;
import io.github.lucasoliveira28.medicalappointmentapi.entities.Appointment;
import io.github.lucasoliveira28.medicalappointmentapi.entities.Doctor;
import io.github.lucasoliveira28.medicalappointmentapi.entities.DoctorAvailability;
import io.github.lucasoliveira28.medicalappointmentapi.entities.Patient;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorAvailabilityRepository;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorRepository;
import io.github.lucasoliveira28.medicalappointmentapi.validations.DoctorAvailabilityRequestValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorAvailabilityService {

    private final DoctorAvailabilityRepository doctorAvailabilityRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorAvailabilityRequestValidation validation;

    @Autowired
    public DoctorAvailabilityService(
            DoctorAvailabilityRepository doctorAvailabilityRepository,
            DoctorRepository doctorRepository,
            DoctorAvailabilityRequestValidation validation
    ) {
        this.doctorAvailabilityRepository = doctorAvailabilityRepository;
        this.doctorRepository = doctorRepository;
        this.validation = validation;
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

    private DoctorAvailabilityResponseDTO buildDoctorAvailabilityAvailableResponseDTO(DoctorAvailability doctorAvailability) {
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

    private DoctorAvailability buildDoctorAvailabilityEntity(DoctorAvailabilityRequestDTO doctorAvailability) {
        return new DoctorAvailability(
                doctorRepository.findDoctorById(doctorAvailability.doctorId()),
                doctorAvailability.startDate(),
                doctorAvailability.endDate()
        );
    }

    public List<DoctorAvailabilityResponseDTO> getAllDoctorAvailabilityScheduled() {
        return doctorAvailabilityRepository.findAllByAvailable(false)
                .stream()
                .map(this::buildDoctorAvailabilityResponseDTO)
                .collect(Collectors.toList());
    }

    public List<DoctorAvailabilityResponseDTO> getAllDoctorAvailabilityAvailable() {
        return doctorAvailabilityRepository.findAllByAvailable(true)
                .stream()
                .map(this::buildDoctorAvailabilityAvailableResponseDTO)
                .collect(Collectors.toList());
    }

    public void saveDoctorAvailability(DoctorAvailabilityRequestDTO dto) {
        validation.availabilityValidation(
                dto.doctorId(),
                dto.startDate(),
                dto.endDate()
        );
        doctorAvailabilityRepository.save(buildDoctorAvailabilityEntity(dto));
    }
}
