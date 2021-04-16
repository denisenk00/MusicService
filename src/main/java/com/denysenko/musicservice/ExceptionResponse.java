package com.denysenko.musicservice;

import com.denysenko.musicservice.exceptions.RestServiceException;
import org.springframework.http.HttpStatus;

public class ExceptionResponse {
    private HttpStatus httpStatus;
    private String massage;
    private String exceptionCode;

    public ExceptionResponse(RestServiceException e) {
        this.httpStatus = e.getHttpStatus();
        this.massage = e.getMassage();
        this.exceptionCode = e.getExceptionCode();
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
