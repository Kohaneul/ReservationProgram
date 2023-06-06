package com.visit.program.ReservationProgram.domain.dao.session;

public abstract class SessionConst {    //접속시 세션 key 값
    public final static String DINNER_PROGRAM="DINNER_PROGRAM";
    public final static String VISITOR_PROGRAM="VISITOR_PROGRAM";
    public final static String ACCESS_METHOD="ACCESS_METHOD";   //접속 수단(컴퓨터 or 모바일 구분을 위함)
    public final static String ACCESS_ID = "ACCESS_ID"; //접근 id : 정해진 url로 접속시 해당 접근 id를 부여
    public final static String EMPLOYEE_ID = "EMPLOYEE_ID"; //직원 아이디 세션값에 저장하여 추가 기재시 설정
    public final static String LOGIN_SUCCESS = "LOGIN_SUCCESS"; //게시글 수정, 삭제를 위한 로그인을 해야함 -> 성공시 해당 세션이 부여됨
    public final static String ADMIN_ID = "ADMIN_ID"; //게시글 수정, 삭제를 위한 로그인을 해야함 -> 성공시 해당 세션이 부여됨


}
