package com.visit.program.ReservationProgram.domain.ex;
/**
 * 수정사항이 없으면 예외 발생 ( 수정내용 반영 x)
 * */
public class NoModificationsEx extends RuntimeException {
    private String message;
    public NoModificationsEx() {
    }


    public NoModificationsEx(String message) {

        super(message);
    }

    public NoModificationsEx(String message, Throwable cause) {
        super(message, cause);
    }

    public NoModificationsEx(Throwable cause) {
        super(cause);
    }

    protected NoModificationsEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
