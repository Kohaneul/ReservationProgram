package com.visit.program.ReservationProgram.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DinnerInfo {
    private Long id;
    private Long employee_id;
    private Long dinner_reservation_id;


    public DinnerInfo(Long employee_id, Long dinner_reservation_id) {
        this.employee_id = employee_id;
        this.dinner_reservation_id = dinner_reservation_id;
    }
}
