package com.visit.program.ReservationProgram.domain.repository;

import com.visit.program.ReservationProgram.domain.dao.UpdateVisitor;
import com.visit.program.ReservationProgram.domain.dao.Visitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * 방문자 CRUD 관련 MAPPER 클래스. (경로 : mybatis/visitor_mapper.xml)
 * */

@Mapper
@Repository
public interface VisitorRepository {

    List<Visitor> findAll();    //방문자 전체 조회
    Visitor findOne(Long id);   //아이디로 조회

    Long saveInfo(Visitor visitor); //방문자 저장
    void deleteInfo(Long id);   //방문자 삭제
    void updateCheckedInfo(@Param("is_checked")boolean is_checked,@Param("checked_date")String checked_date,@Param("id")Long id);   //방문시 체크

    void updateInfo(UpdateVisitor updateVisitor);//방문자 정보 수정

}
