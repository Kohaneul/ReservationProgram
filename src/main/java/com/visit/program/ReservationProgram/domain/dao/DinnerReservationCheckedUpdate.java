package com.visit.program.ReservationProgram.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@AllArgsConstructor
public class DinnerReservationCheckedUpdate {
    private Long id;
    private Boolean is_checked;
    private String checked_date;

}
