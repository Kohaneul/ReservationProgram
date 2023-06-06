package com.visit.program.ReservationProgram.domain.interceptor;
import com.visit.program.ReservationProgram.domain.dao.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
/**
 * 로그인 인터셉터
 * 로그인 성공하면 로그인 관련 세션 (key : SessionConst.LOGIN_SUCCESS) 생성 -> 이 세션이 있어야 게시글의 수정, 삭제 가능
 * */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String requestURI = request.getRequestURI();
        int i = requestURI.lastIndexOf("/");
        requestURI = requestURI.substring(i+1);
        log.info("requestURI={}",requestURI);
        if(session.getAttribute(SessionConst.ADMIN_ID)!=null){
            return true;
        }
        if(session.getAttribute(SessionConst.LOGIN_SUCCESS)==null){
            if(session.getAttribute(SessionConst.DINNER_PROGRAM)!=null){
                response.sendRedirect("/dinner/login/"+requestURI);//로그인 성공을 하지 못하면 로그인 페이지로 다시 돌아감
                return false;
            }
            response.sendRedirect("/reservation/login/"+requestURI);//로그인 성공을 하지 못하면 로그인 페이지로 다시 돌아감
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
