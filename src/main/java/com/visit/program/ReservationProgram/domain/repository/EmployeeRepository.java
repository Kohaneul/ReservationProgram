package com.visit.program.ReservationProgram.domain.repository;

import com.visit.program.ReservationProgram.domain.dao.*;
import com.visit.program.ReservationProgram.domain.dto.Login;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * 직원 조회,수정 관련 MAPPER 클래스.  (mybatis/employee_mapper.xml)
 * 함수명 동일하게 하면 클래스와 쿼리문이 매핑됨.
 * */
@Mapper
@Repository
public interface EmployeeRepository {

    void updatePassword(@Param("password")String password, @Param("id")Long id);    //비밀번호 변경
    String findByName(@Param("id")Long id); //이름 조회

    List<Employee> findAll();   //직원 전체 조회

    Employee findById(@Param("id")Long id); //직원조회 1 : 아이디를 통한 조회

    Employee findByLoginId(String loginId); //직원조회 2 : 로그인 아이디를 통한 조회

}
