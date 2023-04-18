package com.partyh.finder.common.utils;
import org.springframework.transaction.TransactionSystemException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TransactionSystemExceptionMapConverter {

    public static Map<String, String> convert(TransactionSystemException e) {
        Map<String, String> errors = new HashMap<>();
        Throwable cause = e.getRootCause();
        if (cause instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) cause).getConstraintViolations();
            for (ConstraintViolation<?> violation : violations) {
                errors.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
        }
        return errors;
    }
}
