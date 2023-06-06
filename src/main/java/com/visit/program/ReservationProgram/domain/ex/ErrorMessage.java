package com.visit.program.ReservationProgram.domain.ex;
//에러 메세지 문자열 상수로 정의
public abstract class ErrorMessage {
    public final static String NO_REVISE_MSG ="한번 변경한 내용은 수정이 불가능합니다. 관리자에게 문의하세요";
    public final static String ALREADY_CHECKED_MSG ="이미 접수 확인 된 내용은 수정이 불가능합니다. 관리자에게 문의하세요";
    public final static String NO_MODIFICATION_MSG ="수정한 내용이 없습니다. 다시 확인해주세요.";
    public final static String REVISE_COUNT_EXCESS="수정 가능 횟수를 초과하였습니다.(5회 초과시 수정불가)";
    public final static String ALREADY_CHECKED = "이미 체크한 내용입니다.";

}
