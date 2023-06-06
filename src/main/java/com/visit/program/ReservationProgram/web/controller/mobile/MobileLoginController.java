package com.visit.program.ReservationProgram.web.controller.mobile;

import com.visit.program.ReservationProgram.domain.dao.Reservation;
import com.visit.program.ReservationProgram.domain.dao.session.SessionConst;
import com.visit.program.ReservationProgram.domain.dto.Login;
import com.visit.program.ReservationProgram.domain.service.LoginService;
import com.visit.program.ReservationProgram.domain.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/m/reservation")
public class MobileLoginController {
    private final LoginService loginService;
    private final ReservationService reservationService;

    @GetMapping("/login/{reservationId}")
    public String login(@ModelAttribute("login")Login login){
        return "mobile/view/Login";
    }
    @PostMapping("/login/{reservationId}")
    public String login2(@PathVariable("reservationId")Long reservationId,@Valid @ModelAttribute("login")Login login,BindingResult bindingResult,
                         HttpSession session, Model model){
        findEmployeeId(reservationId,login,bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("errorMsg","아이디 or 비밀번호가 틀렸습니다.");
            return "mobile/view/Login";
        }
        session.setAttribute(SessionConst.LOGIN_SUCCESS,UUID.randomUUID().toString());
        return "redirect:/m/reservation/info/update/"+reservationId;
    }

    private void findEmployeeId(Long reservationId, Login login, BindingResult bindingResult){
        Reservation res = reservationService.findOne(reservationId);
        if(loginService.loginMember(login,res.getId())==null){
            bindingResult.reject("globalError","아이디 / 비밀번호가 틀렸습니다.");
        }
    }




}
