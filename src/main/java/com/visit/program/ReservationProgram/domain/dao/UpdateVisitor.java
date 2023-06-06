package com.visit.program.ReservationProgram.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
/**
 * 방문객 정보 수정을 위한 클래스
 * */
@Getter
@Setter
@NoArgsConstructor
public class UpdateVisitor {
    @NotNull
    private Long id;
    @NotEmpty
    private String employee_name;
    @NotEmpty
    private String name;
    @NotEmpty
    private String purpose;
    private String company;
    @NotEmpty
    private String phone_number;
    @NotEmpty
    private String visit_date1;
    private String visit_date2;
    @NotEmpty
    private String write_date;
    @NotEmpty
    private String revised_write_date;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
    private String withPerson;
    @NotNull
    private Integer count;
    private String checked_date;
    @NotNull
    private Boolean is_checked;

    public UpdateVisitor(String loginId, String password, String employee_name, String name, String phone_number, String company, String visit_date1,
                         String visit_date2, String withPerson, String purpose, String write_date, Integer count, Boolean is_checked, String checked_date,Long id) {
        this.loginId = loginId;
        this.password = password;
        this.employee_name = employee_name;
        this.id = id;
        this.name = name;
        this.purpose = purpose;
        this.company = company;
        this.phone_number = phone_number;
        this.visit_date1 = visit_date1;
        this.visit_date2 = visit_date2;
        this.write_date = write_date;
        this.revised_write_date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm"));    //객체 생성시 현재 시각 셋팅
        this.withPerson = withPerson;
        this.count = count;
        this.is_checked = is_checked;
        this.checked_date = checked_date;
    }
}