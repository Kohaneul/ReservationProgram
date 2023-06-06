package com.visit.program.ReservationProgram.domain.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 예약 정보 관련 클래스 (방문자 전체 조회시 해당 클래스 사용)
 * */
@Getter
@Setter
@NoArgsConstructor
public class Reservation {
    private Long id;
    private Long visitor_id;
    private Long employee_id;
    private String name;
    private String employee_name;
    private String visit_Date1;
    private String visit_Date2;
    private String phone_number;
    private String withPerson;
    private String purpose;
    private String company;
    private Boolean is_checked;

    public Reservation(Long id, String name, String employee_name, String visit_Date1, String visit_Date2, String phone_number, String withPerson, String company, Boolean is_checked, Long visitor_id
    , Long employee_id, String purpose){
           this.id = id;
           this.name = name;
           this.employee_name = employee_name;
           this.visit_Date1 = visit_Date1;
           this.visit_Date2 = visit_Date2;
           this.phone_number = phone_number;
           this.withPerson = withPerson;
           this.company =company;
           this.is_checked = is_checked;
           this.visitor_id = visitor_id;
           this.employee_id = employee_id;
           this.purpose = purpose;
    }


}
