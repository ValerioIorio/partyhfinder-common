package com.partyh.finder.common.exception.impl;

import com.partyh.finder.common.exception.PFException;

import java.util.Map;

public class PFNotFoundException extends PFException {
    public PFNotFoundException(String message) {
        super(message);
    }

    public PFNotFoundException(String message, Map<String, String> details) {
        super(message, details);
    }
}
