package com.visit.program.ReservationProgram.web.controller.path;

import org.springframework.web.bind.annotation.SessionAttribute;

import static com.visit.program.ReservationProgram.domain.dao.session.SessionConst.ACCESS_METHOD;

public abstract class AbstractPath {

    public String change(@SessionAttribute(ACCESS_METHOD)String method,String path){
        String result = path;
        if(method.equals("MOBILE")){
            result = call();
        }
        return result;
    }
    protected abstract String call();


}
