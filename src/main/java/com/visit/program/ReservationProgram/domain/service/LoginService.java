package com.visit.program.ReservationProgram.domain.service;

import com.visit.program.ReservationProgram.domain.dao.Employee;
import com.visit.program.ReservationProgram.domain.dto.Login;
import com.visit.program.ReservationProgram.domain.repository.EmployeeRepository;
import com.visit.program.ReservationProgram.domain.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {
    private final LoginRepository loginRepository;

    public Long loginMember(Login login,Long id){
        return loginRepository.loginMember(login.getLoginId(),login.getPassword(),id);
    }

    public Long loginEmp(Login login, Long id){
        return loginRepository.loginEmp(login.getLoginId(),login.getPassword(),id);
    }

    public Boolean loginAdmin(Login login, Long id){
        return loginRepository.loginAdmin(login.getLoginId(), login.getPassword(), id);
    }

}
