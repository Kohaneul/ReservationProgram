package com.visit.program.ReservationProgram.domain.ex;
/**
 * 5회 이상 수정 불가이므로 이를 초과하면 예외 발생
 * */
public class ReviseCountExcess extends RuntimeException{
    public ReviseCountExcess() {
        super();
    }

    public ReviseCountExcess(String message) {
        super(message);
    }

    public ReviseCountExcess(String message, Throwable cause) {
        super(message, cause);
    }

    public ReviseCountExcess(Throwable cause) {
        super(cause);
    }

    protected ReviseCountExcess(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
