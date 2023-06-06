package com.visit.program.ReservationProgram.domain.repository;

import com.visit.program.ReservationProgram.domain.dao.Reservation;
import com.visit.program.ReservationProgram.domain.dao.ReservationInfo;
import com.visit.program.ReservationProgram.domain.dao.SaveReservationInfo;
import com.visit.program.ReservationProgram.domain.dto.MyReservationDTO;
import com.visit.program.ReservationProgram.domain.dto.ReservationDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * 방문자+직원 CRUD 관련 MAPPER 클래스. (경로 : mybatis/reservation_info_mapper.xml)
 * */
@Mapper
@Repository
public interface ReservationRepository {
    List<Reservation> findAll();    //방문객 전체 조회
    void saveInfo(SaveReservationInfo reservation); //방문객 저장

    void deleteInfo(Long id);   //방문객 정보 삭제
    void updateCheckedInfo(@Param("is_checked")boolean is_checked, @Param("id") Long id);   //방문객 체크시 업데이트
    Reservation findOne(Long id);   //아이디로 방문내역 조회

    ReservationInfo findInfoOne(Long id);    //아이디로 방문내역 조회

    List<Reservation> findAllDTO(ReservationDTO reservationDTO); //아이디로 방문내역 조회 (직원용)
    List<Reservation> findAllDTO2(ReservationDTO reservationDTO); //아이디로 방문내역 조회 (경비실용)

    List<Reservation> findMyVisitors(MyReservationDTO reservationDTO);



}
