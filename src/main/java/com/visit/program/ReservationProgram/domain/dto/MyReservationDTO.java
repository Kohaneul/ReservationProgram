package com.visit.program.ReservationProgram.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//나의 예약내역을 조회하기 위한 클래스
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyReservationDTO {
    private String date1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));   //방문기간1 : (컴퓨터 시스템상)오늘 날짜로 셋팅
    private String date2;
    @NotNull
    private Long employee_id;   //employee 테이블의 pk 값 조회
    private Boolean is_checked;
    private String name;


}
