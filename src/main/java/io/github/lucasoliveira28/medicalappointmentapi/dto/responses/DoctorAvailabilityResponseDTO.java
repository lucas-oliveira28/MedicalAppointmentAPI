package io.github.lucasoliveira28.medicalappointmentapi.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DoctorAvailabilityResponseDTO(

        Long id,

        DoctorResponseDTO doctor,

        @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm")
        LocalDateTime startDate,

        @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm")
        LocalDateTime endDate,

        Boolean available

) {

}
