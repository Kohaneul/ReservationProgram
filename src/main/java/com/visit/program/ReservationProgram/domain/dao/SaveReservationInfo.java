package com.visit.program.ReservationProgram.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Reservation_info 테이블을 저장하기 위한 클래스
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveReservationInfo {
    private Long visitor_id;
    private Long employee_id;
    private Boolean is_checked;
}
