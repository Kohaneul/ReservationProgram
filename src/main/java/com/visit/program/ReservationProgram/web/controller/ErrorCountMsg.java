package com.visit.program.ReservationProgram.web.controller;

import com.visit.program.ReservationProgram.domain.dao.SaveVisitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
@Slf4j
public abstract class ErrorCountMsg {

    private static Map<String,String> set(SaveVisitor visitor) throws IllegalAccessException {
        Map<String,String> setMap = new TreeMap<>();
        for (Field field : visitor.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(Arrays.toString(field.getAnnotations()).contains("Not")){
                setMap.put(field.getName(),String.valueOf(field.get(visitor)));
            }
        }
        return setMap;
    }

    public static String setErrorMsg(SaveVisitor visitor, BindingResult bindingResult) throws IllegalAccessException {
        StringBuilder builder = new StringBuilder();
        Map<String,String> setMap = set(visitor);
        int cnt = getCnt(builder, setMap);
        if(cnt==0){
            return null;
        }
        else{
            String str = "다음 표시된 항목을 확인 후 다시 입력 해주세요 : "+builder.toString();
            bindingResult.reject("globalError",str);
            return str;
        }
    }

    private static int getCnt(StringBuilder builder, Map<String, String> setMap){
        List<String> strArr = Arrays.asList("이름(접견자) ", "로그인아이디", "이름(방문자) ", "비밀번호 ", "비밀번호(확인) ","연락처 ","목적 ","방문일자1 ", "방문일자2 ");
        int idx = 0;
        int cnt = 0;
        for (String s : setMap.keySet()) {
            if (!StringUtils.hasText(setMap.get(s))) {
                cnt++;
                builder.append(strArr.get(idx));}
                idx++;}
        return cnt;
    }


}
