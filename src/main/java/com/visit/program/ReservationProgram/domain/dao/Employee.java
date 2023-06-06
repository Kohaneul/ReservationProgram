package com.visit.program.ReservationProgram.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * employee 테이블 매핑 클래스
 * */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Long id;
    private String loginId;
    private String part_name;
    private Boolean is_admin;
    private String employee_name;
    private String password;
    private String phone_number;
}
