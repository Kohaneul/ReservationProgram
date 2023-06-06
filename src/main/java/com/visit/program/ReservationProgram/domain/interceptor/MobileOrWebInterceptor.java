package com.visit.program.ReservationProgram.domain.interceptor;

import com.visit.program.ReservationProgram.domain.dao.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * 처음 접속시 접속수단 모바일 / 컴퓨터로 접속했는지 해당 클래스에서 걸러줌
 * 모바일이면 세션값(key : ACCESS_METHOD) MOBILE 이라고 저장 , 컴퓨터라면 COMPUTER로 저장
 * */
@Slf4j
public class MobileOrWebInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userAgent = request.getHeader("user-agent");
        HttpSession session = request.getSession();
        boolean mobile1 = userAgent.matches( ".*(iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson).*");
        boolean mobile2 = userAgent.matches(".*(LG|SAMSUNG|Samsung).*");
        if(mobile1 || mobile2){
            session.setAttribute(SessionConst.ACCESS_METHOD,"MOBILE");  //모바일이면 세션값(key : ACCESS_METHOD) MOBILE 이라고 저장
        }
        else{
            session.setAttribute(SessionConst.ACCESS_METHOD,"COMPUTER");    //컴퓨터라면 COMPUTER 로 저장
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
