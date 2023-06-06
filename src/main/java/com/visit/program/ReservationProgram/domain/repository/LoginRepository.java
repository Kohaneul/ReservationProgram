package com.visit.program.ReservationProgram.domain.repository;

import com.visit.program.ReservationProgram.domain.dao.Employee;
import com.visit.program.ReservationProgram.domain.dto.Login;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * 로그인 조회 관련 MAPPER 클래스. (경로 : mybatis/login_mapper.xml)
 * 함수명 동일하게 하면 클래스와 쿼리문이 매핑됨.
 * */
@Mapper
@Repository
public interface LoginRepository {
    Long loginMember(@Param("loginId")String loginId,@Param("password")String password, @Param("id")Long id);
    Long loginEmp(@Param("loginId")String loginId,@Param("password")String password, @Param("id")Long id);
    Boolean loginAdmin(@Param("loginId")String loginId,@Param("password")String password,@Param("id")Long id);
}
