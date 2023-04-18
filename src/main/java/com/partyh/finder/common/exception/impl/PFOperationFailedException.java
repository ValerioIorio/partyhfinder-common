package com.partyh.finder.common.exception.impl;

import com.partyh.finder.common.exception.PFException;

import java.util.Map;

public class PFOperationFailedException extends PFException {
    public PFOperationFailedException(String message) {
        super(message);
    }

    public PFOperationFailedException(String message, Map<String, String> details) {
        super(message, details);
    }
}
