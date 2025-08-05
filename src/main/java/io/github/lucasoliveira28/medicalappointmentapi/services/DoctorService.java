package io.github.lucasoliveira28.medicalappointmentapi.services;

import io.github.lucasoliveira28.medicalappointmentapi.entities.Doctor;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor findDoctorByCrm(String crm) {
        return doctorRepository.findDoctorByCrm(crm);
    }

    public Doctor findDoctorByName(String name) {
        return doctorRepository.findDoctorByName(name);
    }

    public Doctor findDoctorById(UUID id) {
        return doctorRepository.findDoctorById(id);
    }

    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    public void CreateDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    public Doctor updateActive(UUID id, Doctor doctorActive) {
        Doctor doctor = doctorRepository.findDoctorById(id);
        doctor.setActive(doctorActive.getActive());
        return doctorRepository.save(doctor);
    }

}
