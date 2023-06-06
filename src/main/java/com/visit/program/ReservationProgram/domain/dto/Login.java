package com.visit.program.ReservationProgram.domain.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
/**
 * 로그인 클래스
 * */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Login {
    @NotEmpty(message="id가 누락되었습니다.")
    private String loginId;     //로그인 아이디
    @NotEmpty(message="패스워드가 누락되었습니다.")
    private String password;    //비밀번호


}
