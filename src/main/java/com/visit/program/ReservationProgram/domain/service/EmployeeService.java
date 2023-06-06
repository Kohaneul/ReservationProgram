package com.visit.program.ReservationProgram.domain.service;

import com.visit.program.ReservationProgram.domain.dao.Employee;
import com.visit.program.ReservationProgram.domain.dto.Login;
import com.visit.program.ReservationProgram.domain.repository.EmployeeRepository;
import com.visit.program.ReservationProgram.domain.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    public Employee findById(Long id){
        return employeeRepository.findById(id);
    }
    public Employee findByLoginId(String loginId){
        return employeeRepository.findByLoginId(loginId);
    }

    public List<Employee> findAll(){return employeeRepository.findAll();}
    public String findByName(Long id){
        return employeeRepository.findByName(id);
    }

    @Transactional
    public void updatePassword(String password, Long id){
        employeeRepository.updatePassword(password,id);
    }



}
