package io.github.lucasoliveira28.medicalappointmentapi.services;

import io.github.lucasoliveira28.medicalappointmentapi.dto.requests.DoctorRequestDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.requests.update.DoctorUpdateRequestDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.responses.DoctorResponseDTO;
import io.github.lucasoliveira28.medicalappointmentapi.entities.Doctor;
import io.github.lucasoliveira28.medicalappointmentapi.entities.enums.MedicalSpecialty;
import io.github.lucasoliveira28.medicalappointmentapi.exceptions.RequestErrorException;
import io.github.lucasoliveira28.medicalappointmentapi.exceptions.RequestNotFoundException;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorRepository;
import io.github.lucasoliveira28.medicalappointmentapi.validations.DoctorRequestValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorRequestValidation validation;

    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(this::buildDoctorResponseDTO)
                .collect(Collectors.toList());
    }

    public void saveDoctor(DoctorRequestDTO dto) {
        var entity = buildDoctorEntity(dto);
        doctorRepository.save(entity);
    }

    private Doctor buildDoctorEntity(DoctorRequestDTO dto) {
        if (validation.isNameValid(dto.name())
        && validation.isEmailValid(dto.email())
        && validation.isPhoneValid(dto.phone())
        && validation.isCrmValid(dto.crm())
        && validation.isPasswordValid(dto.password())
        && validation.isMedicalSpecialtyValid(dto.specialty())
        ) {
            return new Doctor(
                    dto.name(), dto.email(), validation.normalizePhone(dto.phone()), dto.crm(), dto.password(), MedicalSpecialty.valueOf(dto.specialty()));
        }
        throw new RequestErrorException("Error on saving doctor");
    }

    private DoctorResponseDTO buildDoctorResponseDTO(Doctor doctor) {
        return new DoctorResponseDTO(
                doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getPhone(), doctor.getCrm(), doctor.getSpecialty(), doctor.getActive()
        );
    }

    public DoctorResponseDTO getDoctor(Map<String, String> params) {

        if  (params.containsKey("name")) {
            var name = params.get("name");
            Doctor doctor = doctorRepository.findDoctorByName(name);
            if (doctor != null) {
                return buildDoctorResponseDTO(doctor);
            }
        }
        if  (params.containsKey("crm")) {
            var crm = params.get("cpf");
            Doctor doctor = doctorRepository.findDoctorByCrm(crm);
            if (doctor != null) {
                return buildDoctorResponseDTO(doctor);
            }
        }
        throw new RequestNotFoundException("Doctor not found");
    }

    public DoctorResponseDTO deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findDoctorById(id);
        if (doctor != null) {
            doctorRepository.delete(doctor);
            return buildDoctorResponseDTO(doctor);
        }
        throw new RequestNotFoundException("Doctor not found");
    }

    public DoctorResponseDTO updateDoctor(Long id, DoctorUpdateRequestDTO dto) {
        Doctor doctor = doctorRepository.findDoctorById(id);
        if (doctor != null) {
            if (dto.name() != null) {
                if (validation.isNameValid(dto.name())) {
                    doctor.setName(dto.name());
                }
            }
            if (dto.email() != null) {
                if (validation.isEmailValid(dto.email())) {
                    doctor.setEmail(dto.email());
                }
            }
            if (dto.phone() != null) {
                if (validation.isPhoneValid(dto.phone())) {
                    doctor.setPhone(validation.normalizePhone(dto.phone()));
                }
            }
            if (dto.crm() != null) {
                if (validation.isCrmValid(dto.crm())) {
                    doctor.setCrm(dto.crm());
                }
            }
            if (dto.password() != null) {
                if (validation.isPasswordValid(dto.password())) {
                    doctor.setPassword(dto.password());
                }
            }
            if (dto.specialty() != null) {
                if (validation.isMedicalSpecialtyValid(dto.specialty())) {
                    doctor.setSpecialty(MedicalSpecialty.valueOf(dto.specialty()));
                }
            }
            if (dto.active() != null) {
                if (validation.isActiveValid(dto.active().toString())) {
                    doctor.setActive(dto.active());
                }
            }
            doctorRepository.save(doctor);
            return buildDoctorResponseDTO(doctor);
        }
        throw new RequestNotFoundException("Doctor not found");
    }
}
