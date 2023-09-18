package com.denysenko.musicservice.exceptions;

import org.springframework.http.HttpStatus;

public class RemoteServiceException extends RuntimeException {
    private HttpStatus httpStatus;
    private String massage;
    private String exceptionCode;

    public RemoteServiceException(HttpStatus httpStatus, String massage, String exceptionCode) {
        this.httpStatus = httpStatus;
        this.massage = massage;
        this.exceptionCode = exceptionCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
