package com.visit.program.ReservationProgram.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * 예약내역 검색
 * */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    @Nullable
    private String date1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));   //방문날짜1(From)
    @Nullable
    private String date2;   //방문날짜2(To)
    @Nullable
    private Boolean is_checked; //방문여부(체크O/체크X)
    @Nullable
    private String name;    //방문자 이름


}
