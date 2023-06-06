package com.visit.program.ReservationProgram.web.controller;

import com.visit.program.ReservationProgram.domain.dao.*;
import com.visit.program.ReservationProgram.domain.dao.session.SessionConst;
import com.visit.program.ReservationProgram.domain.dto.DinnerInfoDTO;
import com.visit.program.ReservationProgram.domain.ex.ErrorMessage;
import com.visit.program.ReservationProgram.domain.service.DinnerService;
import com.visit.program.ReservationProgram.domain.service.EmployeeService;
import com.visit.program.ReservationProgram.web.controller.path.CutStr;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static java.lang.Boolean.TRUE;

@Controller
@Slf4j
@RequestMapping("/dinner/info")
@RequiredArgsConstructor
public class DinnerController {

    private final DinnerService service;
    private final EmployeeService employeeService;
    private final ThreadLocal<List<DinnerReservationInfo>> dinnerReservationInfoThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<List<DatePerQty>> datePerQtyThreadLocal = new ThreadLocal<>();
    private static DinnerInfoDTO dinnerInfoDTOStatic;
    @GetMapping("/rapigen")
    public String enterPage(HttpSession session){

        session.setAttribute(SessionConst.DINNER_PROGRAM,"dinner");
        session.setAttribute(SessionConst.ACCESS_ID, UUID.randomUUID().toString().substring(0,8));
        log.info("session={}",session.getAttribute(SessionConst.DINNER_PROGRAM));
        return "redirect:/dinner/info/dateOfQty";
    }


    @GetMapping("/all")
    public String save(@ModelAttribute("reservationDTO")DinnerInfoDTO dinnerInfoDTO,HttpSession session,Model model){
        List<DinnerReservationInfo> reservations = null;
        if(dinnerInfoDTOStatic==null){
            dinnerInfoDTO.setVisit_date1(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            dinnerInfoDTO.setVisit_date2(LocalDateTime.now().plusDays(10).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            dinnerInfoDTOStatic = dinnerInfoDTO;
            reservations = service.findAll();
        }
        if(dinnerInfoDTO.getEmployee_name()!=null|| dinnerInfoDTO.getVisit_date2()!=null || dinnerInfoDTO.getVisit_date1()!=null){
            dinnerInfoDTOStatic = dinnerInfoDTO;
        }
        reservations = service.findAllDTO(dinnerInfoDTOStatic);

        dinnerReservationInfoThreadLocal.set(reservations);
        model.addAttribute("reservations",dinnerReservationInfoThreadLocal.get());
        if(session.getAttribute(SessionConst.ADMIN_ID)!=null){
            return "view2/All2";
        }
        return "view2/All1";
    }

    @ModelAttribute(name="renewDate")
    public String renewDate(){
        return  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss"));
    }



    @ModelAttribute(name = "datePerQtyList")
    public  List<DatePerQty> datePerQtyList(@ModelAttribute("dateDTO")SelectDateDTO dateDTO){
        datePerQtyThreadLocal.set(service.findAllDatePerQty(dateDTO));
        if(dateDTO.getVisit_date1()==null && dateDTO.getVisit_date2()==null){
            datePerQtyThreadLocal.set(service.findAllDatePerQtyFrom7Days());
        }
        return datePerQtyThreadLocal.get();
    }

    @PostMapping("/send")
    @ResponseBody
    public HashMap<String,Object> send(@RequestBody HashMap<String,Object> sendDTO){
        String loginId = String.valueOf(sendDTO.get("loginId"));
        Employee employee = employeeService.findByLoginId(loginId);
        sendDTO.replace("phone_number",employee.getPhone_number());
        sendDTO.replace("employee_name",employee.getEmployee_name());
        sendDTO.replace("part_name",employee.getPart_name());

        return sendDTO;
    }


    @PostMapping("/childSend")
    @ResponseBody
    public HashMap<String,Object> childSend(@RequestBody HashMap<String,Object> childDTO){
        Long id=Long.valueOf(String.valueOf(childDTO.get("id")));
        Integer qty=Integer.valueOf(String.valueOf(childDTO.get("qty")));
        service.updateDateOfQty(new UpdateDateOfQty(id,qty));
        childDTO.replace("qty",qty);
        return childDTO;
    }


    @GetMapping("/dateOfQty")
    public String dateOfQty(){
        return "view2/ViewDateOfQty";
    }


    @GetMapping("/dateOfQty/{id}")
    public String dateOfQty(@PathVariable("id") Long id, @ModelAttribute("updateQty")UpdateDateOfQty updateQty){
        DateOfQty dateOfQty = service.selectDateOfQty(id);
        updateQty.setQty(dateOfQty.getQty());
        updateQty.setId(id);
        return "view2/SetQty";
    }
    @PostMapping("/dateOfQty/{id}")
    public String searchDateOfQty(@ModelAttribute("updateQty")UpdateDateOfQty updateQty){
        service.updateDateOfQty(updateQty);
//        List<DatePerQty> datePerQtyList = service.findAllDatePerQtyFrom14Days();
//        model.addAttribute("datePerQtyList",datePerQtyList);
        return "redirect:/dinner/info/dateOfQty";
    }


    @GetMapping("/{now_date}/save")
    public String vDatePerSave(@PathVariable("now_date")String now_date,@ModelAttribute("reservationSave")DinnerReservationSave reservationSave,Model model){
        Integer lastQty = service.findLastQty(now_date);
        reservationSave.setVisit_date(now_date);
        model.addAttribute("hiddenQty",lastQty);
        return "view2/SaveForm";
    }


    @PostMapping("/{now_date}/save")
    public String vDatePerSave2(@PathVariable("now_date")String now_date,@Valid @ModelAttribute("reservationSave")DinnerReservationSave reservationSave, BindingResult bindingResult,Model model){
        Integer lastQty = service.findLastQty(now_date);

        model.addAttribute("hiddenQty",lastQty);
        if(bindingResult.hasErrors()){
            return "view2/SaveForm";
        }
        Long savedId = service.save(reservationSave);
        Employee employee = employeeService.findByLoginId(reservationSave.getLoginId());
        service.saveInfo(new SaveDinnerInfo(employee.getId(), savedId));
        return "redirect:/dinner/info/all";
    }

    @GetMapping("/update/check/{id}")
    public String checkUpdate(@PathVariable("id")Long id,HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
        DinnerReservation before = service.findOne(id);
        HttpSession session = request.getSession();
        if(session.getAttribute(SessionConst.ADMIN_ID)==null){
            if(before.getIs_checked()){
                CutStr.ex(ErrorMessage.ALREADY_CHECKED_MSG, request, response,1);
            }
        }
        DinnerReservationUpdate reservation = new DinnerReservationUpdate
                (before.getId(),before.getLoginId(),before.getEmployee_name(),before.getPhone_number(),before.getVisit_date(),before.getContents()
                        ,before.getQty());
        model.addAttribute("reservation",reservation);
        return "redirect:/dinner/info/update/{id}";
    }
    @GetMapping("/{id}")
    public String detailView(@PathVariable("id")Long id,Model model){
        DinnerReservation reservation = service.findOne(id);
        model.addAttribute("reservation",reservation);
        return "view2/ViewOne";
    }


    @GetMapping("/update/{id}")
    public String updateView(@PathVariable("id")Long id, Model model){

        DinnerReservation before = service.findOne(id);
        DinnerReservationUpdate reservation = new DinnerReservationUpdate
                (before.getId(),before.getLoginId(),before.getEmployee_name(),before.getPhone_number(),before.getVisit_date(),before.getContents()
                        ,before.getQty());

        Integer lastQty = service.findLastQty(before.getVisit_date());
        model.addAttribute("hiddenQty",lastQty);


        model.addAttribute("reservation",reservation);
        model.addAttribute("hiddenValue",reservation.getVisit_date());
        return "view2/UpdateForm";

    }

    @PostMapping("/update/{id}")
    public String updateView2(@PathVariable("id")Long id,@Valid @ModelAttribute("reservation")DinnerReservationUpdate reservation,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "view2/UpdateForm";
        }
        service.updateInfo(reservation);
        return  "redirect:/dinner/info/{id}";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")Long id){
        log.info("삭제 로직");
        service.delete(id);
        log.info("삭제 완료 id={}",id);
        return "redirect:/dinner/info/all";
    }

    @RequestMapping("/download")
    public ResponseEntity<String> downLoadCSV(@ModelAttribute("reservationDTO")DinnerInfoDTO dinnerInfoDTO){
        List<DinnerReservationInfo> reservations = service.findAllDTO(dinnerInfoDTOStatic);
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type","text/csv; charset=MS949");
        String fileName = "Dinner["+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"))+"]";
        header.add("Content-Disposition", "attachment;filename=\"" + fileName + ".csv\"");
        return new ResponseEntity<>(content(reservations),header, HttpStatus.CREATED);
    }

    private String content(List<DinnerReservationInfo> employees){
        String data = "";
        data +="구분, no, 예약일, 인원, 소속 부서, 예약자, 등록 시간, 확인여부(미확인,확인)\n";
            for (int i = 0; i < employees.size(); i++) {
                data += "석식 예약"+",";
		        data += (i+1)+ ",";
                data += employees.get(i).getVisit_date() + ",";
                data += employees.get(i).getQty() + ",";
                data += employees.get(i).getPart_name() + ",";
                data += employees.get(i).getEmployee_name() + ",";
                data += employees.get(i).getWrite_date() + ",";
                data += employees.get(i).getIs_checked() + "\n";
            }
            return data;
        }
    @GetMapping("/click/{id}")
    public String checkDinner(@PathVariable("id")Long id){
        DinnerReservation reservation = service.findOne(id);
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss"));
        DinnerReservation dinnerReservation = service.findOne(id);
        service.updateCheckedReservation(new DinnerReservationCheckedUpdate(dinnerReservation.getId(), reservation.getIs_checked(), now));
        log.info("완료 id={}",dinnerReservation.getIs_checked());
        return "redirect:/dinner/info/all";
    }

    @GetMapping("/click/all/{idLists}")
    public String checkIdLists(@PathVariable("idLists")String idLists){
        String[] list = idLists.split(",");
        for(int i = 0;i<list.length;i++){
            Long id = Long.parseLong(list[i]);
            service.updateCheckedReservation(new DinnerReservationCheckedUpdate(id, TRUE, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss"))));
        }
        return "redirect:/dinner/info/all";
    }


    @GetMapping("/admin/logOut")
    public String adminLogOut(HttpSession session){
        session.removeAttribute(SessionConst.ADMIN_ID);
        return "redirect:/dinner/info/dateOfQty";
    }

    @GetMapping("/checkInfo")
    public String checkedDinnerInfo(){
        return "view2/checkInfo";
    }

    @PostMapping("/checkInfo")
    public String checkedDinnerInfo2(@RequestParam String employee_name, RedirectAttributes redirectAttributes){
        Long id = service.findByName(employee_name);
        if(id==null){
            return "redirect:/dinner/info/checkInfo";
        }
        service.updateAteInfo(id);
        redirectAttributes.addAttribute("employee_name",employee_name);
        return "redirect:/dinner/info/confirm";
    }

    @GetMapping("/dateOfQty/{id}/end")
    public String endQty(@PathVariable("id")Long id){
      service.updateOfQty(id);
        return "redirect:/dinner/info/dateOfQty";
    }
    @ResponseBody
    @GetMapping("/confirm")
    public String confirmInfo(@RequestParam String employee_name){
        return employee_name+"님 확인되었습니다.";
    }
    }


