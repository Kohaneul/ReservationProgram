package com.visit.program.ReservationProgram.web.controller;
import com.visit.program.ReservationProgram.domain.ex.*;
import com.visit.program.ReservationProgram.web.controller.path.CutStr;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;

@Slf4j
@ControllerAdvice
@RequestMapping("/error")
public class MyErrorController implements ErrorController {

    @ExceptionHandler(BindException.class)
    public void NumberFormatEx(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Error error = new Error();
        String message = error.getMessage();
        CutStr.ex(message,request,response,2);
    }

//    private void ex(String message, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String referURL = request.getHeader("REFERER");
//        response.setContentType("text/html; charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        referURL=referURL.substring(referURL.indexOf("r")-1);
//        out.println("<script>alert('"+message+"'); location.href='"+referURL+"';</script>");
//        out.flush();
//    }

    @ExceptionHandler(AlreadyCheckedEx.class)
    public void AlreadyCheckedEx(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CutStr.ex(ErrorMessage.ALREADY_CHECKED,request,response,2);
    }
    @ExceptionHandler(NoModificationsEx.class)
    public void NoModificationEx(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CutStr.ex(ErrorMessage.NO_MODIFICATION_MSG,request,response,2);
    }
    @ExceptionHandler(ReviseCountExcess.class)
    public void ReviseCountExcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CutStr.ex(ErrorMessage.REVISE_COUNT_EXCESS,request,response,2);
    }


}