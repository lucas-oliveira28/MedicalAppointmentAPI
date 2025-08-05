package io.github.lucasoliveira28.medicalappointmentapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.lucasoliveira28.medicalappointmentapi.entities.enums.MedicalSpeciality;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_doctor")
public class Doctor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String email;
    private String crm;

    @Enumerated(EnumType.STRING)
    private MedicalSpeciality speciality;

    private String phone;
    private Boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor")
    private Set<Appointment> appointments;

    public Doctor() {
    }

    public Doctor(UUID id, String name, String email, String crm, MedicalSpeciality speciality, String phone, Boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.crm = crm;
        this.speciality = speciality;
        this.phone = phone;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public MedicalSpeciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(MedicalSpeciality speciality) {
        this.speciality = speciality;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(id, doctor.id) && Objects.equals(name, doctor.name) && Objects.equals(crm, doctor.crm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, crm);
    }
}
