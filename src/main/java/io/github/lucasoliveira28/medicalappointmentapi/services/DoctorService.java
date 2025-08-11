package io.github.lucasoliveira28.medicalappointmentapi.services;

import io.github.lucasoliveira28.medicalappointmentapi.dto.requests.DoctorRequestDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.requests.update.DoctorUpdateRequestDTO;
import io.github.lucasoliveira28.medicalappointmentapi.dto.responses.DoctorResponseDTO;
import io.github.lucasoliveira28.medicalappointmentapi.entities.Doctor;
import io.github.lucasoliveira28.medicalappointmentapi.entities.enums.MedicalSpecialty;
import io.github.lucasoliveira28.medicalappointmentapi.exceptions.RequestNotFoundException;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorRepository;
import io.github.lucasoliveira28.medicalappointmentapi.validations.DoctorRequestValidation;
import io.github.lucasoliveira28.medicalappointmentapi.validations.GeneralValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRequestValidation validation;
    private final DoctorRepository doctorRepository;
    private final GeneralValidation generalValidation;

    @Autowired
    public DoctorService(DoctorRequestValidation validation, DoctorRepository doctorRepository, GeneralValidation generalValidation) {
        this.validation = validation;
        this.doctorRepository = doctorRepository;
        this.generalValidation = generalValidation;
    }

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
        generalValidation.isNameValid(dto.name());
        validation.isEmailValid(dto.email());
        validation.isPhoneValid(dto.phone());
        validation.isCrmValid(dto.crm());
        generalValidation.isPasswordValid(dto.password());
        validation.isMedicalSpecialtyValid(dto.specialty());
        return new Doctor(
                dto.name(), dto.email(), generalValidation.normalizePhone(dto.phone()),
                dto.crm(), dto.password(), MedicalSpecialty.valueOf(dto.specialty())
        );
    }

    private DoctorResponseDTO buildDoctorResponseDTO(Doctor doctor) {
        return new DoctorResponseDTO(
                doctor.getId(), doctor.getName(), doctor.getEmail(),
                doctor.getPhone(), doctor.getCrm(), doctor.getSpecialty(), doctor.getActive()
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
                generalValidation.isNameValid(dto.name());
                doctor.setName(dto.name());
            }
            if (dto.email() != null) {
                validation.isEmailValid(dto.email());
                doctor.setEmail(dto.email());
            }
            if (dto.phone() != null) {
                validation.isPhoneValid(dto.phone());
                doctor.setPhone(generalValidation.normalizePhone(dto.phone()));
            }
            if (dto.crm() != null) {
                validation.isCrmValid(dto.crm());
                doctor.setCrm(dto.crm());
            }
            if (dto.password() != null) {
                generalValidation.isPasswordValid(dto.password());
                doctor.setPassword(dto.password());
            }
            if (dto.specialty() != null) {
                validation.isMedicalSpecialtyValid(dto.specialty());
                doctor.setSpecialty(MedicalSpecialty.valueOf(dto.specialty()));
            }
            if (dto.active() != null) {
                generalValidation.isActiveValid(dto.active().toString());
                doctor.setActive(dto.active());
            }
            doctorRepository.save(doctor);
            return buildDoctorResponseDTO(doctor);
        }
        throw new RequestNotFoundException("Doctor not found");
    }
}
