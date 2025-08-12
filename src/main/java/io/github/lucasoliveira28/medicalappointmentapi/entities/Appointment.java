package io.github.lucasoliveira28.medicalappointmentapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.lucasoliveira28.medicalappointmentapi.entities.enums.AppointmentStatus;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm")
    private LocalDateTime date;

    private String reason;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private String reasonForCancellation;

    @ManyToOne()
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne()
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToOne
    @JoinColumn(name = "availability_id", unique = true)
    private DoctorAvailability availability;

    public Appointment() {
    }

    public Appointment(LocalDateTime date, String reason, AppointmentStatus status, Patient patient, Doctor doctor, DoctorAvailability availability) {
        this.date = date;
        this.reason = reason;
        this.status = status;
        this.patient = patient;
        this.doctor = doctor;
        this.availability = availability;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getReasonForCancellation() {
        return reasonForCancellation;
    }

    public void setReasonForCancellation(String reasonForCancellation) {
        this.reasonForCancellation = reasonForCancellation;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public DoctorAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(DoctorAvailability availability) {
        this.availability = availability;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
