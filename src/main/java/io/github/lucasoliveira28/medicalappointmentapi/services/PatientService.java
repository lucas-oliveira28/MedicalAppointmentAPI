package io.github.lucasoliveira28.medicalappointmentapi.services;

import io.github.lucasoliveira28.medicalappointmentapi.entities.Doctor;
import io.github.lucasoliveira28.medicalappointmentapi.entities.Patient;
import io.github.lucasoliveira28.medicalappointmentapi.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient findPatientByCpf(String cpf) {
        return patientRepository.findPatientByCpf(cpf);
    }

    public Patient findPatientByName(String name) {
        return patientRepository.findPatientByName(name);
    }

    public Patient findPatientById(Long id) {
        return patientRepository.findPatientById(id);
    }

    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }

    public void CreatePatient(Patient patient) {
        patientRepository.save(patient);
    }

    public Patient updateActive(Long id, Patient patientActive) {
        Patient patient = patientRepository.findPatientById(id);
        patient.setActive(patientActive.getActive());
        return patientRepository.save(patient);
    }

}
