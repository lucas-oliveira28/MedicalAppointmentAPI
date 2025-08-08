package io.github.lucasoliveira28.medicalappointmentapi.config;

import io.github.lucasoliveira28.medicalappointmentapi.entities.Appointment;
import io.github.lucasoliveira28.medicalappointmentapi.entities.Doctor;
import io.github.lucasoliveira28.medicalappointmentapi.entities.Patient;
import io.github.lucasoliveira28.medicalappointmentapi.entities.enums.AppointmentStatus;
import io.github.lucasoliveira28.medicalappointmentapi.entities.enums.MedicalSpeciality;
import io.github.lucasoliveira28.medicalappointmentapi.repository.AppointmentRepository;
import io.github.lucasoliveira28.medicalappointmentapi.repository.DoctorRepository;
import io.github.lucasoliveira28.medicalappointmentapi.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void run(String... args) throws Exception {

        Doctor d1 = new Doctor(null, "Lucas Monteiro", "lucas.monteiro@gmail.com",
                "CRM/PE 12345", MedicalSpeciality.CARDIOLOGY, "983900042", true);
        doctorRepository.save(d1);

        Patient p1 = new Patient("João José", "joao.jose@gmail.com",
                "912345678", "12345678900", "joao123");
        patientRepository.save(p1);
    }
}
