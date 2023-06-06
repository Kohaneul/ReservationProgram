package com.visit.program.ReservationProgram.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class DinnerReservationUpdate {

    @NotNull
    private Long id;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String employee_name;
    @NotEmpty
    @Pattern(regexp = "^0([0-9]{1,2})([0-9]{7,8})$")
    private String phone_number;
    @NotEmpty
    private String visit_date;
    @NotEmpty
    private String contents;
    @NotNull
    private Integer qty;
    @NotEmpty
    private String revised_write_date;


    public DinnerReservationUpdate(Long id, String loginId, String employee_name, String phone_number, String visit_date, String contents, Integer qty) {
        this.id = id;
        this.loginId = loginId;
        this.employee_name = employee_name;
        this.phone_number = phone_number;
        this.visit_date = visit_date;
        this.contents = contents;
        this.qty = qty;
        this.revised_write_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm")).toString();
    }
}
