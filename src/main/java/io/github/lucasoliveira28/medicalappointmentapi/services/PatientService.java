package io.github.lucasoliveira28.medicalappointmentapi.services;

import io.github.lucasoliveira28.medicalappointmentapi.dto.requests.update.PatientUpdateRequestDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.responses.PatientResponseDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.requests.PatientRequestDTO;
import io.github.lucasoliveira28.medicalappointmentapi.entities.Patient;
import io.github.lucasoliveira28.medicalappointmentapi.exceptions.RequestErrorException;
import io.github.lucasoliveira28.medicalappointmentapi.exceptions.RequestNotFoundException;
import io.github.lucasoliveira28.medicalappointmentapi.repository.PatientRepository;
import io.github.lucasoliveira28.medicalappointmentapi.validations.RequestValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private RequestValidation validation;

    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(this::buildPatientResponseDTO)
                .collect(Collectors.toList());
    }

    public void savePatient(PatientRequestDTO dto) {
        var entity = buildPatientEntity(dto);
        patientRepository.save(entity);
    }

    private Patient buildPatientEntity(PatientRequestDTO dto) {
        if (validation.isNameValid(dto.name())
        && validation.isEmailValid(dto.email())
        && validation.isPhoneValid(dto.phone())
        && validation.isCpfValid(dto.cpf())
        && validation.isPasswordValid(dto.password())
        ) {
            return new Patient(
                    dto.name(), dto.email(), validation.normalizePhone(dto.phone()), dto.cpf(), dto.password());
        }
        throw new RequestErrorException("Error on saving patient");
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

    public PatientResponseDTO updatePatient(Long id, PatientUpdateRequestDTO dto) {
        Patient patient = patientRepository.findPatientById(id);
        if (patient != null) {
            if (validation.isNameValid(dto.name())) {
                patient.setName(dto.name());
            }
            if (validation.isEmailValid(dto.email())) {
                patient.setEmail(dto.email());
            }
            if (validation.isPhoneValid(dto.phone())) {
                patient.setPhone(dto.phone());
            }
            if (validation.isCpfValid(dto.cpf())) {
                patient.setCpf(dto.cpf());
            }
            if (validation.isPasswordValid(dto.password())) {
                patient.setPassword(dto.password());
            }
            patientRepository.save(patient);
            return buildPatientResponseDTO(patient);
        }
        throw new RequestNotFoundException("Patient not found");
    }
}
