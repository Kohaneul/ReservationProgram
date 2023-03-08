package com.visit.program.ReservationProgram.web.controller;

import com.visit.program.ReservationProgram.domain.dao.Reservation;
import com.visit.program.ReservationProgram.domain.dao.session.SessionConst;
import com.visit.program.ReservationProgram.domain.dto.Login;
import com.visit.program.ReservationProgram.domain.service.LoginService;
import com.visit.program.ReservationProgram.domain.service.ReservationService;
import com.visit.program.ReservationProgram.web.controller.path.AbstractPath;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class LoginController {
    private final LoginService loginService;
    private final ReservationService reservationService;
    @ModelAttribute(name="renewDate")
    public String renewDate(){
        return  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy/MM/dd hh:mm:ss"));
    }
    @GetMapping("/login/{reservationId}")
    public String login(@SessionAttribute(SessionConst.ACCESS_METHOD)String method,@PathVariable("reservationId")Long reservationId,@ModelAttribute("login")Login login){
       String url = "view/Login";
        AbstractPath path = new AbstractPath() {
            @Override
            protected String call() {
                return "redirect:/m/reservation/login/{reservationId}";
            }
        };
       return path.change(method,url);
    }

    @PostMapping("/login/{reservationId}")
    public String login2(@PathVariable("reservationId")Long reservationId,@Valid @ModelAttribute("login")Login login,BindingResult bindingResult, HttpSession session, Model model){
        Reservation res = reservationService.findOne(reservationId);
        findEmployeeId(res.getVisitor_id(),login,bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("errorMsg","아이디 or 비밀번호가 틀렸습니다.");
            return "view/Login";
        }
        if(session.getAttribute(SessionConst.EMPLOYEE_ID)==null){
            session.setAttribute(SessionConst.EMPLOYEE_ID,res.getEmployee_id());
        }
            session.setAttribute(SessionConst.LOGIN_SUCCESS,UUID.randomUUID().toString());
        return "redirect:/reservation/info/update/"+reservationId;
    }




    private void findEmployeeId(Long id, Login login, BindingResult bindingResult){
        Long employeeId = loginService.loginMember(login,id);
        if(employeeId==null){
            bindingResult.reject("globalError","아이디 / 비밀번호가 틀렸습니다.");
        }
    }


}
