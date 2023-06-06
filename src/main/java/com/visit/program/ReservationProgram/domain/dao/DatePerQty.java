package com.visit.program.ReservationProgram.domain.dao;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DatePerQty {
    private Long id;
    private String dayOfTheWeek;
    private String now_date;
    private Integer real_qty;
    private Integer qty;
    private Integer last_qty;

}
