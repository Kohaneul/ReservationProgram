package com.visit.program.ReservationProgram.domain.interceptor;

import com.visit.program.ReservationProgram.domain.dao.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * 정해진 PATH 접속시
 *  - 직원용 : reservation/info/all/rapigen_employee   -> 저장 화면으로 리다이랙트
 *  - 경비실 : reservation/info/all/rapigen_security   -> 전체 조회 화면으로 리다이랙트
 *  세션값(key : ACCESS_ID) 생셩
 *  만약 세션값이 없거나 정해진 PATH로 접속되지 않으면 "index.html"로 리다이랙트
 * */
@Slf4j
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if(session.getAttribute(SessionConst.DINNER_PROGRAM)==null && session.getAttribute(SessionConst.ACCESS_ID)==null){
            response.sendRedirect("/");
//            response.sendRedirect("/dinner/info/all"); //만약 세션값이 없거나 정해진 PATH로 접속되지 않으면  SESSION(KEY:ACCESS_ID) 생성되지 않음. "/"로 리다이랙트
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}