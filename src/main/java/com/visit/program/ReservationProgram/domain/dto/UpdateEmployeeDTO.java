package com.visit.program.ReservationProgram.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
/**
 * 직원정보 수정 DTO 클래스
 * */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeDTO {
    @NotNull
    private Long id;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String employee_name;
    @NotEmpty
    private String beforePassword;  //기존 비밀번호

    @NotEmpty
    private String password;    //바꾸려는 비밀번호
    @NotEmpty
    private String passwordCheck;   //비밀번호(확인용) -> password와 일치해야함
}
