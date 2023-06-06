package com.visit.program.ReservationProgram.web.controller.path;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class PathChange {
    public static String setAccessMethod(String access,String uri){
        int idx = uri.indexOf(":") + 2;
        StringBuilder sb = new StringBuilder();

        if(!uri.contains("redirect:/")){
            if(access.equals("MOBILE")){
                sb.append("mobile/");
            }
            sb.append(uri);
        }
        return sb.toString();
    }

}
