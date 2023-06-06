package com.visit.program.ReservationProgram.domain.dao;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * visitor 테이블 매핑 클래스
 *
 * */
@Getter
@Setter
@NoArgsConstructor
public class Visitor {
    private Long id;
    private String loginId;
    private String password;
    private String employee_name;
    private String name;
    private String phone_number;
    private String company;
    private String visit_date1;
    private String visit_date2;
    private String withPerson;
    private String purpose;
    private String write_date;
    private int count;
    private String revised_write_date;
    private String checked_date = null;
    private Boolean is_checked;
    public Visitor(String loginId, String password, String employee_name,String name,String phone_number, String company,String visit_date1, String visit_date2,String withPerson,String purpose, String write_date,
                   String revised_write_date, int count,boolean is_checked){
        this.loginId = loginId;
        this.password = password;
        this.employee_name  = employee_name;
        this.name = name;
        this.phone_number = phone_number;
        this.company = company;
        this.visit_date1 = visit_date1;
        this.visit_date2 = visit_date2;
        this.withPerson = withPerson;
        this.purpose = purpose;
        this.write_date = write_date;
        this.revised_write_date = revised_write_date;
        this.count = count;
        this.is_checked = is_checked;

    }


}
