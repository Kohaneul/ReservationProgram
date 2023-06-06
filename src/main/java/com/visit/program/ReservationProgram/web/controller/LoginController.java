package com.visit.program.ReservationProgram.web.controller;

import com.visit.program.ReservationProgram.domain.dao.DinnerReservation;
import com.visit.program.ReservationProgram.domain.dao.Employee;
import com.visit.program.ReservationProgram.domain.dao.Reservation;
import com.visit.program.ReservationProgram.domain.dao.session.SessionConst;
import com.visit.program.ReservationProgram.domain.dto.Login;
import com.visit.program.ReservationProgram.domain.service.DinnerService;
import com.visit.program.ReservationProgram.domain.service.EmployeeService;
import com.visit.program.ReservationProgram.domain.service.LoginService;
import com.visit.program.ReservationProgram.domain.service.ReservationService;
import com.visit.program.ReservationProgram.web.controller.path.AbstractPath;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final DinnerService dinnerService;
    private final EmployeeService employeeService;
    private final ReservationService reservationService;

    @ModelAttribute(name = "renewDate")
    public String renewDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy/MM/dd hh:mm:ss"));
    }

    @GetMapping("/reservation/login/{reservationId}")
    public String login(@SessionAttribute(SessionConst.ACCESS_METHOD) String method, @PathVariable("reservationId") Long reservationId, @ModelAttribute("login") Login login) {
        String url = "view/Login";
        AbstractPath path = new AbstractPath() {
            @Override
            protected String call() {
                return "redirect:/m/reservation/login/{reservationId}";
            }
        };
        return path.change(method, url);
    }

    @PostMapping("/reservation/login/{reservationId}")
    public String login2(@PathVariable("reservationId") Long reservationId, @Valid @ModelAttribute("login") Login login, BindingResult bindingResult, HttpSession session, Model model) {
        Reservation res = reservationService.findOne(reservationId);
        findEmployeeId(1, res.getVisitor_id(), login, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMsg", "아이디 or 비밀번호가 틀렸습니다.");
            return "view/Login";
        }
        if (session.getAttribute(SessionConst.EMPLOYEE_ID) == null) {
            session.setAttribute(SessionConst.EMPLOYEE_ID, res.getEmployee_id());
        }
        session.setAttribute(SessionConst.LOGIN_SUCCESS, UUID.randomUUID().toString());
        return "redirect:/reservation/info/update/" + reservationId;
    }

    @GetMapping("/dinner/info/admin")
    public String AdminLogin(@ModelAttribute("login") Login login,HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(SessionConst.LOGIN_SUCCESS);
        return "view2/Login";
    }

    @PostMapping("/dinner/info/admin")
    public String AdminLogin2(@Valid @ModelAttribute("login") Login login, BindingResult bindingResult, HttpSession session, Model model) {
        Employee emp = employeeService.findByLoginId(login.getLoginId());
        if (emp == null || loginService.loginAdmin(login, emp.getId()) == null) {
            bindingResult.reject("globalError", "아이디 / 비밀번호가 틀렸거나 관리자 권한이 없습니다.");
            model.addAttribute("errorMsg", "아이디 / 비밀번호가 틀렸거나 관리자 권한이 없습니다.");
        }
         else {
            session.setAttribute(SessionConst.ADMIN_ID, UUID.randomUUID().toString().substring(0, 5) + "/" + emp.getId());
            return "redirect:/dinner/info/dateOfQty";
        }
        return "view2/Login";
    }

    @GetMapping("/dinner/login/{id}")
    public String dinnerLogin(@PathVariable("id")Long id, @ModelAttribute("login")Login login){
        return "view2/Login";
    }

    @PostMapping("/dinner/login/{id}")
    public String dinnerLogin2(@PathVariable("id")Long id, @Valid @ModelAttribute("login")Login login, BindingResult bindingResult
    , Model model, HttpServletRequest request){
        DinnerReservation dinnerReservation = dinnerService.findOne(id);
        findEmployeeId(2,id,login,bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("errorMsg","아이디 or 비밀번호가 틀렸습니다.");
            return "view2/Login";
        }
        HttpSession session = request.getSession();
        if(session.getAttribute(SessionConst.EMPLOYEE_ID)==null){
            session.setAttribute(SessionConst.EMPLOYEE_ID,dinnerReservation.getLoginId());
        }
        session.setAttribute(SessionConst.LOGIN_SUCCESS,UUID.randomUUID().toString());

        return "redirect:/dinner/info/update/{id}";
    }





    private void findEmployeeId(int num, Long id, Login login, BindingResult bindingResult){
        Long employeeId = null;
        if(num==1){
            employeeId = loginService.loginMember(login,id);

        }
        if(num==2){
            employeeId = loginService.loginEmp(login, id);
        }
        if(employeeId==null){
            bindingResult.reject("globalError","아이디 / 비밀번호가 틀렸습니다.");
        }


    }



}
