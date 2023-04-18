package com.partyh.finder.common.exception;

import lombok.Getter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class ResponseError {
    private final int code;
    private final String message;
    private final Map<String, String> details;

    public ResponseError(Exception e, HttpStatus code) {
        this.code = code.value();
        this.message = getMessage(e);
        details = new LinkedHashMap<>();
    }

    private static String getMessage(Exception e) {
        Throwable rootCause = ExceptionUtils.getRootCause(e);
        return rootCause.getClass().getName() +":"+ rootCause.getMessage();
    }

    public ResponseError(Exception e, HttpStatus code, Map<String, String> details) {
        this.code = code.value();
        this.message = e.getMessage();
        this.details = details;
    }

    public ResponseError(String message, HttpStatus code, Map<String, String> details) {
        this.code = code.value();
        this.message = message;
        this.details = details;
    }

    @Override
    public String toString() {
        if(details.isEmpty()) {
            return "ResponseError: {" +
                    "\n   http status: " + code +
                    "\n   message: " + message +  "}";
        } else {
            return "ResponseError: {" +
                    "\n   http status: " + code +
                    "\n   message: " + message +
                    "\n   details: " + details +  "}";
        }
    }
}
