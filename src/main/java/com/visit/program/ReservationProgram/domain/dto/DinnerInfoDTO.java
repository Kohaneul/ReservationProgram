package com.visit.program.ReservationProgram.domain.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DinnerInfoDTO {
    private String employee_name;
    private String visit_date1;
    private String visit_date2;


}
