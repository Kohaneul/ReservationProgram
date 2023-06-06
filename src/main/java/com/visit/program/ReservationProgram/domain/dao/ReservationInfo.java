package com.visit.program.ReservationProgram.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 *  reservation_info 매핑 클래스
 * */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationInfo {
    private Long id;
    private Long visitor_id;
    private Long employee_id;
    private Boolean is_checked;

}