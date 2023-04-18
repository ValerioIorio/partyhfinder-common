package com.partyh.finder.common.exception.impl;

import com.partyh.finder.common.exception.PFException;

import java.util.Map;

public class PFValidationException extends PFException {
    public PFValidationException(String message) {
        super(message);
    }

    public PFValidationException(String message, Map<String, String> details) {
        super(message, details);
    }
}
