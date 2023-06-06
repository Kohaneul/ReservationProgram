package com.visit.program.ReservationProgram.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DinnerReservationInfo {
    //dinner_reservation
    private Long id;
    private String loginId;
    private Long dinner_reservation_id;
    private String employee_name;
    private String part_name;
    private Integer qty;
    private String contents;
    private String visit_date;  //예약일
    private String write_date;  //등록시간
    private Boolean is_checked;
}
