package io.github.lucasoliveira28.medicalappointmentapi.services;

import io.github.lucasoliveira28.medicalappointmentapi.dto.requests.update.PatientUpdateRequestDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.responses.PatientResponseDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.requests.PatientRequestDTO;
import io.github.lucasoliveira28.medicalappointmentapi.entities.Patient;
import io.github.lucasoliveira28.medicalappointmentapi.exceptions.RequestNotFoundException;
import io.github.lucasoliveira28.medicalappointmentapi.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(this::buildPatientResponseDTO)
                .collect(Collectors.toList());
    }

    //TODO: Validator for name length;
    //TODO: Validator for email, phone and cpf if already exists;
    //TODO: Refactor the method for validating dto info;
    public void savePatient(PatientRequestDTO dto) {
        var entity = buildPatientEntity(dto);
        patientRepository.save(entity);
    }

    private Patient buildPatientEntity(PatientRequestDTO dto) {
        return new Patient(
                dto.name(), dto.email(), dto.phone(), dto.cpf(), dto.password());
    }

    private PatientResponseDTO buildPatientResponseDTO(Patient patient) {
        return new PatientResponseDTO(
                patient.getName(), patient.getEmail(), patient.getPhone(), patient.getCpf(), patient.getActive()
        );
    }

    public PatientResponseDTO getPatient(Map<String, String> params) {

        if  (params.containsKey("name")) {
            var name = params.get("name");
            Patient patient = patientRepository.findPatientByName(name);
            if (patient != null) {
                return buildPatientResponseDTO(patient);
            }
        }
        if  (params.containsKey("cpf")) {
            var cpf = params.get("cpf");
            Patient patient = patientRepository.findPatientByCpf(cpf);
            if (patient != null) {
                return buildPatientResponseDTO(patient);
            }
        }
        throw new RequestNotFoundException("Patient not found");
    }

    public PatientResponseDTO deletePatient(Long id) {
        Patient patient = patientRepository.findPatientById(id);
        if (patient != null) {
            patientRepository.delete(patient);
            return buildPatientResponseDTO(patient);
        }
        throw new RequestNotFoundException("Patient not found");
    }

    //TODO: Validator for name length;
    //TODO: Validator for email form;
    //TODO: Validator for email, phone and cpf if already exists;
    public PatientResponseDTO updatePatient(Long id, PatientUpdateRequestDTO dto) {
        Patient patient = patientRepository.findPatientById(id);
        if (patient != null) {
            if (dto.name() != null && !dto.name().isEmpty()) {
                patient.setName(dto.name());
            }
            if (dto.email() != null && !dto.email().isEmpty()) {
                patient.setEmail(dto.email());
            }
            if (dto.phone() != null && !dto.phone().isEmpty()) {
                patient.setPhone(dto.phone());
            }
            if (dto.cpf() != null && !dto.cpf().isEmpty()) {
                patient.setCpf(dto.cpf());
            }
            if (dto.password() != null && !dto.password().isEmpty()) {
                patient.setPassword(dto.password());
            }
            patientRepository.save(patient);
            return buildPatientResponseDTO(patient);
        }
        throw new RequestNotFoundException("Patient not found");
    }
}
