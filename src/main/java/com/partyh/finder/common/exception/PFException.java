package com.partyh.finder.common.exception;

import lombok.Getter;

import java.util.Map;
@Getter
public class PFException extends RuntimeException{
    private String message;
    private Map<String, String> details;


    public PFException(String message) {
        this.message = message;
    }

    public PFException(String message, Map<String, String> details) {
        this.message = message;
        this.details = details;
    }
}
