package com.partyh.finder.common.exception.impl;

import com.partyh.finder.common.exception.PFException;

import java.util.Map;

public class PFIdNullException extends PFException {
    public PFIdNullException(String message) {
        super(message);
    }

    public PFIdNullException(String message, Map<String, String> details) {
        super(message, details);
    }
}
