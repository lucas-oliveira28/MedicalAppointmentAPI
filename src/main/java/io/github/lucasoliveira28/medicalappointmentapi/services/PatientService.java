package io.github.lucasoliveira28.medicalappointmentapi.services;

import io.github.lucasoliveira28.medicalappointmentapi.dto.PatientResponseDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.PatientRequestDTO;
import io.github.lucasoliveira28.medicalappointmentapi.entities.Patient;
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
        return patientRepository.findAll().stream().map(
                patient -> new PatientResponseDTO(
                        patient.getName(),
                        patient.getEmail(),
                        patient.getPhone(),
                        patient.getCpf(),
                        patient.getActive()
                )
        ).collect(Collectors.toList());
    }

    public void savePatient(PatientRequestDTO dto) {
        var entity = buildPatientEntity(dto);
        patientRepository.save(entity);
    }

    private Patient buildPatientEntity(PatientRequestDTO dto) {
        return new Patient(
                dto.name(), dto.email(), dto.phone(), dto.cpf(), dto.active()
        );
    }

    private PatientResponseDTO buildPatientResponseDTO(Patient patient) {
        return new PatientResponseDTO(
                patient.getName(), patient.getEmail(), patient.getPhone(), patient.getCpf(), patient.getActive()
        );
    }

    public PatientResponseDTO getPatient(Map<String, String> params) {

        PatientResponseDTO response = new PatientResponseDTO(null, null, null, null, null);

        if  (params.containsKey("name")) {
            var name = params.get("name");
            Patient patient = patientRepository.findPatientByName(name);
            if (patient != null) {
                response = buildPatientResponseDTO(patient);
            }
            else  {
                throw new RuntimeException("Paciente com Nome '" + name + "' não encontrado");
            }

        }
        if  (params.containsKey("cpf")) {
            var cpf = params.get("cpf");
            Patient patient = patientRepository.findPatientByCpf(cpf);
            if (patient != null) {
                response = buildPatientResponseDTO(patient);
            }
            else  {
                throw new RuntimeException("Paciente com CPF '" + cpf + "' não encontrado");
            }
        }

        return response;
    }
}
