package com.visit.program.ReservationProgram.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DinnerReservation {
    //dinner_reservation
     private Long id;
    private String loginId;
    private String part_name;
    private String employee_name;
    private String phone_number;
    private String visit_date;
    private String contents;
    private Integer qty;
    private String password;
    private String write_date;
    private String revised_write_date;
    private Boolean is_checked;
    private String checked_date;

    public DinnerReservation(String loginId, String part_name, String employee_name, String phone_number, String visit_date, String contents, Integer qty, String password, String write_date, String revised_write_date, Boolean is_checked, String checked_date) {
        this.loginId = loginId;
        this.part_name = part_name;
        this.employee_name = employee_name;
        this.phone_number = phone_number;
        this.visit_date = visit_date;
        this.contents = contents;
        this.qty = qty;
        this.password = password;
        this.write_date = write_date;
        this.revised_write_date = revised_write_date;
        this.is_checked = is_checked;
        this.checked_date = checked_date;
    }
}
