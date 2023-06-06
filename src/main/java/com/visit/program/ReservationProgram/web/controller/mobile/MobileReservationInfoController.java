package com.visit.program.ReservationProgram.web.controller.mobile;

import com.visit.program.ReservationProgram.domain.dao.*;
import com.visit.program.ReservationProgram.domain.dao.session.SessionConst;
import com.visit.program.ReservationProgram.domain.dto.MyReservationDTO;
import com.visit.program.ReservationProgram.domain.dto.ReservationDTO;
import com.visit.program.ReservationProgram.domain.ex.AlreadyCheckedEx;
import com.visit.program.ReservationProgram.domain.ex.ErrorMessage;
import com.visit.program.ReservationProgram.domain.ex.NoModificationsEx;
import com.visit.program.ReservationProgram.domain.ex.ReviseCountExcess;
import com.visit.program.ReservationProgram.domain.service.EmployeeService;
import com.visit.program.ReservationProgram.domain.service.ReservationService;
import com.visit.program.ReservationProgram.domain.service.VisitorService;
import com.visit.program.ReservationProgram.web.controller.ErrorCountMsg;
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
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/m/reservation/info")
public class MobileReservationInfoController {
    private final ReservationService reservationService;
    private final EmployeeService employeeService;
    private final VisitorService visitorService;
    @ModelAttribute(name="renewDate")
    public String renewDate(){
        return  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy/MM/dd hh:mm:ss"));
    }

    @GetMapping("/{employee_id}/all")
    public String viewMyVisitors(@PathVariable("employee_id") Long employee_id, @ModelAttribute("reservationDTO") MyReservationDTO reservationDTO, Model model
            ){
        Employee emp = employeeService.findById(employee_id);
        String loginId = emp.getLoginId();
        reservationDTO.setEmployee_id(employee_id);
        List<Reservation> reservations = reservationService.findMyVisitors(reservationDTO);
        model.addAttribute("reservations",reservations);
        model.addAttribute("loginId",loginId);
        return "mobile/view/viewMyVisitor";
    }
    @GetMapping("/save")
    public String saveInfo(@ModelAttribute("visitor")SaveVisitor visitor,HttpSession session){
        String path = "mobile/view/SaveForm";
        setData(visitor, session);
        return path;
    }
    private String date(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    public void setData(@ModelAttribute("visitor") SaveVisitor visitor, HttpSession session) {
        visitor.setVisit_date1(date());
        visitor.setVisit_date2(date());
        if(session.getAttribute(SessionConst.EMPLOYEE_ID)!=null) {
            long empId = Long.parseLong(session.getAttribute(SessionConst.EMPLOYEE_ID).toString());
            Employee employee = employeeService.findById(empId);
            visitor.setEmployee_name(employee.getEmployee_name());
            visitor.setLoginId(employee.getLoginId());
        }
    }

    @PostMapping("/save")
    public String saveInfo(@Valid @ModelAttribute(name = "visitor") SaveVisitor visitor, BindingResult bindingResult,Model model,HttpSession session
    ) throws IllegalAccessException {
        String path = "mobile/view/SaveForm";
        String wrongPhoneNumber = wrongPhoneNumber(visitor.getPhone_number());
        if(wrongPhoneNumber!=null){
            model.addAttribute("wrongPhoneNumber",wrongPhoneNumber);
        }
        if (bindingResult.hasErrors()) {
            String errorMsg = ErrorCountMsg.setErrorMsg(visitor,bindingResult);
            model.addAttribute("errorMsg",errorMsg);
        }
        else {
                path="redirect:/m/reservation/info/all";
                Visitor visitor1 = visitorService.findOne(visitorService.saveInfo(visitor));
                Employee employee = employeeService.findByLoginId(visitor.getLoginId());
                session.setAttribute(SessionConst.EMPLOYEE_ID,employee.getId());
                reservationService.saveInfo(new SaveReservationInfo(visitor1.getId(), employee.getId(), visitor1.getIs_checked()));
            }

            return path;
        }


    private String wrongPhoneNumber(String phoneNumber){
        String regExp = "^0([0-9]{1,2})([0-9]{7,8})$";
        if(!Pattern.matches(regExp, phoneNumber)){
            return "[연락처] 형식 오류 : '-'을 제외한 형식으로 입력해주세요";
        }
        return null;
    }

    @RequestMapping("/click/{id}")
    public void clickReservation(@ModelAttribute("reservationDTO") ReservationDTO reservationDTO, @PathVariable(name = "id") Long id,
                                 HttpServletRequest request, HttpServletResponse response) throws IOException {
        Reservation reservation = reservationService.findOne(id);
        Long visitor_id = reservation.getVisitor_id();
        Visitor visitor = visitorService.findOne(visitor_id);
        if(visitor.getIs_checked()){
            throw new AlreadyCheckedEx();
        }

        visitorService.updateCheckedInfo(visitor_id);
        reservationService.updateCheckedInfo(id);
        response.sendRedirect(redirectURL(request,response));
    }

    private String redirectURL(HttpServletRequest request, HttpServletResponse response){
        String referURL = request.getHeader("REFERER");
        response.setContentType("text/html; charset=UTF-8");
        referURL=referURL.substring(referURL.indexOf("r")-1);
        return referURL;
    }


    @GetMapping("/{id}")
    public String viewReservationOne(@PathVariable(name = "id") Long id, Model model,HttpSession session) {
        ReservationInfo reservationInfo = reservationService.findInfo(id);
        Long visitorId = reservationInfo.getVisitor_id();
        Visitor visitor = visitorService.findOne(visitorId);
        model.addAttribute("visitor",visitor);
        model.addAttribute("reservation", reservationInfo);
        session.removeAttribute(SessionConst.LOGIN_SUCCESS);

        return "mobile/view/ViewOne";
    }

    @GetMapping("/update/{reservationId}")
    public String updateInfo(@PathVariable("reservationId") Long reservationId, Model model) {

        Reservation reservation = reservationService.findOne(reservationId);
        Visitor beforeVisitor = visitorService.findOne(reservation.getVisitor_id());
        UpdateVisitor updateVisitor = updateVisitor(beforeVisitor);
        model.addAttribute("reservationId",reservationId);
        model.addAttribute("visitor",updateVisitor);
        return "mobile/view/UpdateForm";
    }

    @PostMapping("/update/{reservationId}")
    public String updateInfo(@PathVariable("reservationId") Long reservationId, @Valid @ModelAttribute(name = "visitor") UpdateVisitor updateVisitor, BindingResult bindingResult, HttpSession session){
        int count = updateVisitor.getCount();
        ReviseCountEx(count);
        NoModificationEx(reservationId,updateVisitor);
        if (bindingResult.hasErrors()) {
            String s = bindingResult.getAllErrors().toString();
            log.info("errors={}",s);
            return "mobile/view/UpdateForm";
        }
        visitorService.updateInfo(updateVisitor);
        session.removeAttribute(SessionConst.LOGIN_SUCCESS);
        return "redirect:/m/reservation/info/{reservationId}";
    }
    public static boolean equalsEmp(Visitor visitor, UpdateVisitor updateVisitor) {
        if(     visitor.getEmployee_name().equals(updateVisitor.getEmployee_name()) &&
                visitor.getPhone_number().equals(updateVisitor.getPhone_number()) &&
                visitor.getName().equals(updateVisitor.getName()) &&
                visitor.getCompany().equals(updateVisitor.getCompany()) &&
                visitor.getWithPerson().equals(updateVisitor.getWithPerson()) &&
                visitor.getPurpose().equals(updateVisitor.getPurpose()) &&
                visitor.getVisit_date1().equals(updateVisitor.getVisit_date1()) &&
                visitor.getVisit_date2().equals(updateVisitor.getVisit_date2())) {
            return true;
        }
        return false;
    }
    private void NoModificationEx(Long id,UpdateVisitor updateVisitor) {
        Reservation reservation=reservationService.findOne(id);
        Visitor visitor = visitorService.findOne(reservation.getVisitor_id());
        if(equalsEmp(visitor, updateVisitor)){
            throw new NoModificationsEx(ErrorMessage.NO_REVISE_MSG);
        }
    }


    private void ReviseCountEx(int count){
        if(count>=5){
            throw new ReviseCountExcess(ErrorMessage.REVISE_COUNT_EXCESS);
        }
    }

    private UpdateVisitor updateVisitor(Visitor visitor) {
        log.info("visitor id={}",visitor.getId());
        return new UpdateVisitor(visitor.getLoginId(),visitor.getPassword(),visitor.getEmployee_name(),visitor.getName(),visitor.getPhone_number(),visitor.getCompany(),visitor.getVisit_date1(),visitor.getVisit_date2()
                ,visitor.getWithPerson(),visitor.getPurpose(),visitor.getWrite_date(),visitor.getCount(),visitor.getIs_checked(),visitor.getChecked_date(),visitor.getId());}


    @GetMapping("/delete/{reservationId}")
    public String deleteInfo(@PathVariable("reservationId") Long reservationId,HttpSession session){
        Long visitorId = reservationService.findOne(reservationId).getVisitor_id();
        reservationService.deleteInfo(reservationId);
        visitorService.deleteInfo(visitorId);
        session.removeAttribute(SessionConst.LOGIN_SUCCESS);

        return "redirect:/m/reservation/info/all";
    }


}