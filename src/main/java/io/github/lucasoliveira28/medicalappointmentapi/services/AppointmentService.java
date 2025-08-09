package io.github.lucasoliveira28.medicalappointmentapi.services;

import io.github.lucasoliveira28.medicalappointmentapi.entities.Appointment;
import io.github.lucasoliveira28.medicalappointmentapi.entities.enums.AppointmentStatus;
import io.github.lucasoliveira28.medicalappointmentapi.repository.AppointmentRepository;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorRepository doctorRepository;

    public Appointment findAppointmentById(Long id) {
        return appointmentRepository.findAppointmentById(id);
    }

    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment updateStatus(Long id, Appointment appointmentStatus) {
        Appointment appointment = appointmentRepository.findAppointmentById(id);
        appointment.setStatus(appointmentStatus.getStatus());
        if (appointmentStatus.getStatus().equals(AppointmentStatus.CANCELLED)) {
            appointment.setReasonForCancellation(appointmentStatus.getReasonForCancellation());
        }
        return appointmentRepository.save(appointment);
    }
}


