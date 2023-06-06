package com.visit.program.ReservationProgram.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DinnerInfoCheckedUpdate {
    private Long id;
    private Boolean is_checked;
}
