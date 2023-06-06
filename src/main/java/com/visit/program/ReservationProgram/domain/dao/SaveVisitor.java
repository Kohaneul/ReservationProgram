package com.visit.program.ReservationProgram.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * visitor 테이블을 저장하기 위한 클래스
 * */
@Getter
@Setter
@NoArgsConstructor
public class SaveVisitor{

    @NotEmpty
    private String employee_name;
    @NotEmpty
    private String name;
    @Pattern(regexp = "^([0-9]{1,20})$")
    @NotEmpty
    private String phone_number;
    private String company;
    @NotEmpty
    private String visit_date1;
    @NotEmpty
    private String visit_date2;
    private String withPerson;
    @NotEmpty
    private String purpose;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String passwordCheck;

    private String write_date;
    private int count;
    private String revised_write_date;
    private Boolean is_checked;

    public SaveVisitor(String loginId, String password,String passwordCheck, String employee_name, String name, String phone_number, String company, String visit_date1, String visit_date2, String withPerson, String purpose) {
        this.loginId = loginId;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.employee_name = employee_name;
        this.name = name;
        this.phone_number = phone_number;
        this.company = company;
        this.visit_date1 = visit_date1;
        this.visit_date2 = visit_date2;
        this.withPerson = withPerson;
        this.purpose = purpose;
        this.count = 0;
        this.is_checked = false;
        this.revised_write_date = null;
    }
}
