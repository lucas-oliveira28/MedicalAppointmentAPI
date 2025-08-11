package io.github.lucasoliveira28.medicalappointmentapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.lucasoliveira28.medicalappointmentapi.entities.enums.MedicalSpecialty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_doctor")
public class Doctor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String crm;
    private String password;

    @Enumerated(EnumType.STRING)
    private MedicalSpecialty specialty;

    private Boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor")
    private Set<Appointment> appointments;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor")
    private Set<DoctorAvailability> availabilities;

    public Doctor() {
    }

    public Doctor(String name, String email, String phone, String crm, String password, MedicalSpecialty specialty) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.crm = crm;
        this.password = password;
        this.specialty = specialty;
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public MedicalSpecialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(MedicalSpecialty specialty) {
        this.specialty = specialty;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<DoctorAvailability> getAvailabilities() {
        return availabilities;
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
