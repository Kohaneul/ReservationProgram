package com.visit.program.ReservationProgram.web.controller;

import com.visit.program.ReservationProgram.domain.dao.Employee;
import com.visit.program.ReservationProgram.domain.dto.UpdateEmployeeDTO;
import com.visit.program.ReservationProgram.domain.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reservation/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/update/{id}")
    public String pwUpdate(@PathVariable("id")Long id, @ModelAttribute("employee") UpdateEmployeeDTO employee){
        setEmployee(id,employee);
        return "view/UpdatePassword";
    }

    private void setEmployee(Long id,UpdateEmployeeDTO employee){
        Employee employeeInfo = employeeService.findById(id);
        employee.setId(employeeInfo.getId());
        employee.setBeforePassword(employeeInfo.getPassword());
        employee.setEmployee_name(employeeInfo.getEmployee_name());
        employee.setLoginId(employeeInfo.getLoginId());
    }
    @PostMapping("/update/{employeeId}")
    public String pwUpdate2( @PathVariable("employeeId")Long id,@Valid @ModelAttribute("employee")UpdateEmployeeDTO employee, BindingResult bindingResult){
        updateCheck(employee,bindingResult);
        if(bindingResult.hasErrors()){
            return "view/UpdatePassword";
        }
        employeeService.updatePassword(employee.getPassword(), id);
        return "redirect:/reservation/info/all";
    }

    private void updateCheck(UpdateEmployeeDTO employee,BindingResult bindingResult){
        if(!employee.getPassword().equals(employee.getPasswordCheck())){
            bindingResult.reject("globalError","비밀번호가 일치하지 않습니다.");
        }
    }


}
