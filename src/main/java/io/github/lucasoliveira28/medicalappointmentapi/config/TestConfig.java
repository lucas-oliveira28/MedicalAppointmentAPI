package io.github.lucasoliveira28.medicalappointmentapi.config;

import io.github.lucasoliveira28.medicalappointmentapi.entities.Doctor;
import io.github.lucasoliveira28.medicalappointmentapi.entities.DoctorAvailability;
import io.github.lucasoliveira28.medicalappointmentapi.entities.enums.MedicalSpecialty;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorAvailabilityRepository;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private DoctorAvailabilityRepository doctorAvailabilityRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public void run(String... args) throws Exception {

        Doctor D1 = new Doctor(
                "Augusto Renan", "augusto.renan@gmail.com", "81912345678",
                "12345-PE", "Augusto123", MedicalSpecialty.CARDIOLOGY);

        doctorRepository.save(D1);

        LocalDateTime st = LocalDateTime.now().plusDays(1);
        LocalDateTime dt = st.plusDays(1);

        DoctorAvailability DA1 = new DoctorAvailability(
                D1, st, dt
        );

        doctorAvailabilityRepository.save(DA1);

    }
}
