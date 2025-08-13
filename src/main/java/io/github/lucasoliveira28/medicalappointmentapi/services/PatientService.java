package io.github.lucasoliveira28.medicalappointmentapi.services;

import io.github.lucasoliveira28.medicalappointmentapi.dto.requests.update.PatientUpdateRequestDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.responses.PatientResponseDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.requests.PatientRequestDTO;
import io.github.lucasoliveira28.medicalappointmentapi.entities.Patient;
import io.github.lucasoliveira28.medicalappointmentapi.entities.enums.PatientGender;
import io.github.lucasoliveira28.medicalappointmentapi.exceptions.RequestNotFoundException;
import io.github.lucasoliveira28.medicalappointmentapi.repository.PatientRepository;
import io.github.lucasoliveira28.medicalappointmentapi.validations.GeneralValidation;
import io.github.lucasoliveira28.medicalappointmentapi.validations.PatientRequestValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientRequestValidation validation;
    private final GeneralValidation generalValidation;

    @Autowired
    public PatientService(PatientRepository patientRepository, PatientRequestValidation validation, GeneralValidation generalValidation) {
        this.patientRepository = patientRepository;
        this.validation = validation;
        this.generalValidation = generalValidation;
    }

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
        validation.isCpfUnique(dto.cpf());
        validation.isEmailUnique(dto.email());
        validation.isPhoneUnique(generalValidation.normalizePhone(dto.phone()));

        return new Patient(
                dto.name(), dto.email(), generalValidation.normalizePhone(dto.phone()),
                dto.cpf(), dto.address(), dto.birthDate(),
                PatientGender.valueOf(dto.gender()), dto.medicalHistory(), dto.password()
        );
    }

    private PatientResponseDTO buildPatientResponseDTO(Patient patient) {
        return new PatientResponseDTO(
                patient.getId(), patient.getName(), patient.getEmail(),
                patient.getPhone(), patient.getCpf(), patient.getAddress(),
                patient.getBirthDate(), patient.getGender(),
                patient.getMedicalHistory(), patient.getActive()
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
            if (dto.name() != null) {
                patient.setName(dto.name());
            }
            if (dto.email() != null) {
                validation.isEmailUnique(dto.email());
                patient.setEmail(dto.email());
            }
            if (dto.phone() != null) {
                validation.isPhoneUnique(generalValidation.normalizePhone(dto.phone()));
                patient.setPhone(generalValidation.normalizePhone(dto.phone()));
            }
            if (dto.cpf() != null) {
                validation.isCpfUnique(dto.cpf());
                patient.setCpf(dto.cpf());
            }
            if (dto.address() != null) {
                patient.setAddress(dto.address());
            }
            if (dto.birthDate() != null) {
                patient.setBirthDate(dto.birthDate());
            }
            if (dto.gender() != null) {
                patient.setGender(PatientGender.valueOf(dto.gender()));
            }
            if (dto.password() != null) {
                patient.setPassword(dto.password());
            }
            if (dto.active() != null) {
                patient.setActive(dto.active());
            }
            patientRepository.save(patient);
            return buildPatientResponseDTO(patient);
        }
        throw new RequestNotFoundException("Patient not found");
    }
}
