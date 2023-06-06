package com.visit.program.ReservationProgram.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class DinnerReservationSave {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String employee_name;
    @NotEmpty
    private String part_name;
    
   // @Pattern(regexp = "^0([0-9]{1,2})([0-9]{7,8})$")
    private String phone_number;
    @NotEmpty
    private String password;
    @NotEmpty
    private String passwordCheck;
    @NotEmpty
    private String visit_date;

    @NotNull
    @Range(min = 1)
    private Integer qty;    
    @NotEmpty
    private String contents;

    public DinnerReservationSave(String loginId, String part_name,String employee_name,  String phone_number, String visit_date, String contents, Integer qty, String password, String passwordCheck) {
        this.loginId = loginId;
        this.employee_name = employee_name;
        this.part_name = part_name;
        this.phone_number = phone_number;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.visit_date = visit_date;
        this.qty = qty;
        this.contents = contents;
    }
}
